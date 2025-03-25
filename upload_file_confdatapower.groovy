def entornoSelect=""
def encoded=""
String content=""
def archivo=""
pipeline {
    agent any
    environment {
        LDESPLIEGUE = 'ONLY_ONE'
    }
    
     parameters { 
        choice(name: 'DEPLOY', choices: ['DESA', 'INTRAGES', 'USOOFICIAL', 'EXTRANET'], description: 'Select an option')
        choice(name: 'PROJECT', choices: ['WSP_ACCEDA','WSP_ACCEDA_CLAVEDEF','WSP_ACCEDA_CLAVEDEF_EXTRANET','WSP_AEAT','WSP_AGT','WSP_API','WSP_ARCHIVA','WSP_BALMIS','WSP_BALMIS_Horus','WSP_BALMIS_Multicita','WSP_CARPETA_CIUDADANO','WSP_CODICE','WSP_CORPORATIVO','WSP_CSN','WSP_CVCDEF','WSP_CVD','WSP_DCORE','WSP_DCORE_anotaREPEMI','WSP_DCORE_anotaREPEREST','WSP_DCORE_DIGITADEF','WSP_DCORE_DIR3DEF','WSP_DCORE_MENSADEF','WSP_DCORE_RCP','WSP_DCORE_RECECUT','WSP_DCORE_RUBRICA','WSP_DCORE_RUBRICAV4','WSP_DCORE_SINFRADEF','WSP_DICODEF_Servicios','WSP_DIGITADEF','WSP_DIR3DEF','WSP_EMS','WSP_DOCUMENTUM','WSP_GEISER','WSP_MAIL','WSP_ISFAS','WSP_MSCBS','WSP_MSCBS_ISFAS','WSP_RECONFAS','WSP_RegistroElectronico','WSP_RUBRICAV4','WSP_SAPROMIL','WSP_SCAN','WSP_SCSP','WSP_SERMAS','WSP_SERMAS_MANTENIMIENTO_CITAS','WSP_SICCOCEN','WSP_SIDAE','WSP_SIGECAR','WSP_SIGLE-Prov','WSP_SIGLE','WSP_SIGLE_EXTERNOS','WSP_SIGMAWEB','WSP_SIPERDEF','WSP_SIPERDEF_ARGO','WSP_SIPERDEF_DCORE','WSP_SIPERDEF_GNOSS','WSP_SIPERDEF_RECECUT','WSP_SIPERDEF_REPEMI','WSP_SIPERDEF_SEDE','WSP_SIPERDEF_SELCON','WSP_SIPERDEF_SICSPSE2','WSP_SIPERDEF_RNA','WSP_SIPERDEF_PORTAL','WSP_SIPERDEF_SIGEI','WSP_SIPERDEF_SISTRAM','WSP_SIPERDEF_TIMEA','WSP_SSRL','WSP_SSRL_ConsingmentService','WSP_TELCON','WSP_TE_EXTRA','WSP_TramitacionEletronica','MPG_AMPS-SLOG','MPG_ACCEDA','MPG_BALANCEO_USOF','MPG_API_HTTP','MPG_CMIS_AUTEN_SSL','MPG_DCORE_ESB','MPG_Publica_WSDL_SERMAS','MPG_SSRL_IN','MPG_Entrada','MPG_DCORE_REPEMI','MPG_DCORE_REPEREST','MPG_DCORE_SOAP','MPG_ISFAS','MPG_ServiciosREST','MPG_SIUCOM','MPG_SECUREBK_SFTP','MPG_SSRL','MPGW-DICOM'], description: 'Select an proyect')
        string(name: 'SCANS', defaultValue: 'vacio', description: 'Insert Scans')
    }
    
stages {
    stage ('Select Domain'){
    steps{
    script{
        def entornos=""
        echo "${env.LDESPLIEGUE}  -- ${params.DEPLOY} ${params.PROJECT} ${params.SCANS}" 
         if(params.DEPLOY  == "DESA"){
			 entornos = "testIBM\nARTEC\nDIVINDES\nLABORATORIO"
         }else if(params.DEPLOY  == "INTRAGES"){
			 entornos = "testIBM\nPREUSOF\nPREPRODUCCION\nPRODUCCION"
         }else if(params.DEPLOY  == "USOOFICIAL"){
			 entornos = "testIBM\nPRODUCCION"
         }else if(params.DEPLOY  == "EXTRANET"){
			 entornos = "testIBM\nPREPRODUCCION\nPRODUCCION"
         }
			entornoSelect  = input(message: 'Interacción Usuario requerido',ok: 'Seleccionar',
            parameters: [choice(name: 'Elección Entorno', choices: "${entornos}", description: '¿Sobre qué entorno deseas desplegar?')])
        
        // Save to variables. Default to empty string if not found.
        inputConfig = entornoSelect
        echo "Domain: ${inputConfig}"
        selectedOptions = env.LDESPLIEGUE
        appSelect = params.PROJECT
        echo "You selected: ${selectedOptions}"
    }
    }
    }
    stage ('Download Repositorio'){
    steps{
    script{
        pathWS = pwd()
        echo "You selected: download Repositorio ${params.DEPLOY}"
        echo "${entornoSelect}"
        def proyecto = params.PROJECT.toLowerCase()
        withCredentials([string(credentialsId: 'urlDatapowerGit', variable: 'SECRET_TEXT')]) {
                       
            def urlRepo="${SECRET_TEXT}/${proyecto}.git"
            echo "${urlRepo}"
            sshagent(['jenkins_Git_SSH_Access']) {
              sh "git clone --branch develop ${urlRepo}"
              echo "El texto secreto es: ${SECRET_TEXT}"
              echo"******************************************"
              echo"****Descarga Proyecto en su rama Develop *"
              echo"******************************************"
            }
        }
        
       
        
        
         withCredentials([string(credentialsId: 'codeBase64', variable: 'SECRET_TEXT')]) {
             
            def pathFile = "${proyecto}/local/disk/${params.PROJECT}/configuration/${params.DEPLOY}/${entornoSelect}"
            echo"****   ${pathWS}/${pathFile}   *"
            sh "cd ${pathWS}/${pathFile}"
           
            def filePathJSON= "${pathWS}/${pathFile}/constantURL.json"
            def fileJSON = new File(filePathJSON)
            def filePathXML = "${pathWS}/${pathFile}/constantURL.xml"
            def fileXML = new File(filePathXML)
            
            if (fileJSON.exists()) {
               println "El fichero JSON existe."
               archivo = "constantURL.json"
            }else  if (fileXML.exists()) {
                println "El fichero XML existe."
                archivo = "constantURL.xml"
                
            } else {
              
                echo"******************************************"
                echo"****  El fichero No existe o ruta   ******"
                echo"******************************************"
                currentBuild.result = 'ABORTED'
                throw new hudson.AbortException('')
            }
            
            content = new File("${pathWS}/${pathFile}/${archivo}").text
        
            echo"******************************************"
            echo"****    fichero Localizado          ******"
            echo"******************************************"
			
			def fileBytes = content.bytes
            // Codificar los bytes a base64
            def base64Encoded = fileBytes.encodeBase64().toString()
			
            echo"******************************************"
            echo"****  base64_encoded_data     *"
            echo"******************************************"
            encoded = "${base64Encoded}" 
            echo"******************************************"
            echo"****   Fichero codificado en Base 64     *"
            echo"******************************************"
        }
        
     
    }
    }
    }
    
    stage ('Crear directorio en Datapower'){
      steps{
        script{
           echo "Directorio de Datapower"
		   
if(params.DEPLOY  == "DESA"){

       sh """
curl --location 'https://srvcceadtpwl01d:5554/mgmt/actionqueue/${entornoSelect}' --header 'Authorization: Basic amVua2luczpqazIwMjQu' --header 'Content-Type: application/json' \
--data '{
    "CreateDir": {
        "Dir": "local:///disk/${params.PROJECT}/configuration"
    }
}'
       """
}else if(params.DEPLOY  == "INTRAGES"){
    def entorno_sel = "${entornoSelect}" 
     def proyect_ = "${params.PROJECT}"
     
sh """
    ssh jenkins@srvcceadpl01 "curl -k --location https://dp01intrages:5554/mgmt/actionqueue/${entorno_sel} --header \\"Authorization: Basic amVua2luczpqazIwMjQu\\" --header \\"Content-Type: application/json\\" --data '{\\"CreateDir\\": {\\"Dir\\": \\"local:///disk/${proyect_}/configuration\\"}}'"
    """
    sleep(20)
    
sh """
    ssh jenkins@srvcceadpl01 "curl -k --location https://dp02intrages:5554/mgmt/actionqueue/${entorno_sel} --header \\"Authorization: Basic amVua2luczpqazIwMjQu\\" --header \\"Content-Type: application/json\\" --data '{\\"CreateDir\\": {\\"Dir\\": \\"local:///disk/${proyect_}/configuration\\"}}'"
    """
}else if(params.DEPLOY  == "USOOFICIAL"){
    
    def entorno_sel = "${entornoSelect}" 
     def proyect_ = "${params.PROJECT}"
     
sh """
    ssh jenkins@srvcceadpl01 "curl -k --location https://dp01usofges:5554/mgmt/actionqueue/${entorno_sel} --header \\"Authorization: Basic amVua2luczpqazIwMjQu\\" --header \\"Content-Type: application/json\\" --data '{\\"CreateDir\\": {\\"Dir\\": \\"local:///disk/${proyect_}/configuration\\"}}'"
    """
    sleep(20)
    
sh """
    ssh jenkins@srvcceadpl01 "curl -k --location https://dp02usofges:5554/mgmt/actionqueue/${entorno_sel} --header \\"Authorization: Basic amVua2luczpqazIwMjQu\\" --header \\"Content-Type: application/json\\" --data '{\\"CreateDir\\": {\\"Dir\\": \\"local:///disk/${proyect_}/configuration\\"}}'"
    """
}else if(params.DEPLOY  == "EXTRANET"){

    def entorno_sel = "${entornoSelect}" 
     def proyect_ = "${params.PROJECT}"
     
sh """
    ssh jenkins@srvcceadpl01 "curl -k --location https://dp01extrages:5554/mgmt/actionqueue/${entorno_sel} --header \\"Authorization: Basic amVua2luczpqazIwMjQu\\" --header \\"Content-Type: application/json\\" --data '{\\"CreateDir\\": {\\"Dir\\": \\"local:///disk/${proyect_}/configuration\\"}}'"
    """
    sleep(20)
    
sh """
    ssh jenkins@srvcceadpl01 "curl -k --location https://dp02extrages:5554/mgmt/actionqueue/${entorno_sel} --header \\"Authorization: Basic amVua2luczpqazIwMjQu\\" --header \\"Content-Type: application/json\\" --data '{\\"CreateDir\\": {\\"Dir\\": \\"local:///disk/${proyect_}/configuration\\"}}'"
    """
    
}
        }
      }
    }
    
    stage ('upload File directorio en Datapower'){
      steps{
        script{
           echo "upload File directorio en Datapower"
           
 withCredentials([string(credentialsId: 'codeBase64', variable: 'SECRET_TEXT')]) {
 
 if(params.DEPLOY  == "DESA"){

       sh """
curl --location --request PUT 'https://srvcceadtpwl01d:5554/mgmt/filestore/${entornoSelect}/local/disk/${params.PROJECT}/configuration/${archivo}' \
--header 'Authorization: Basic amVua2luczpqazIwMjQu' \
--header 'Content-Type: application/json' \
--data '{
            "file": {
                "name":"${archivo}",
                "content":"${encoded}"
			}
}'
       """
}else if(params.DEPLOY  == "INTRAGES"){
      echo "************* INTRAGES"
      
      sh """
ssh jenkins@srvcceadpl01 "curl -k --location --request PUT https://dp01intrages:5554/mgmt/filestore/${entornoSelect}/local/disk/${params.PROJECT}/configuration/${archivo} --header 'Authorization: Basic amVua2luczpqazIwMjQu' --header 'Content-Type: application/json' --data '{\\"file\\": {\\"name\\": \\"${archivo}\\", \\"content\\": \\"${encoded}\\"}}'"
      """
      sleep(10)
     sh """
ssh jenkins@srvcceadpl01 "curl -k --location --request PUT https://dp02intrages:5554/mgmt/filestore/${entornoSelect}/local/disk/${params.PROJECT}/configuration/${archivo} --header 'Authorization: Basic amVua2luczpqazIwMjQu' --header 'Content-Type: application/json' --data '{\\"file\\": {\\"name\\": \\"${archivo}\\", \\"content\\": \\"${encoded}\\"}}'"
      """
}else if(params.DEPLOY  == "USOOFICIAL"){
      echo "************* USOOFICIAL"

      sh """
ssh jenkins@srvcceadpl01 "curl -k --location --request PUT https://dp01usofges:5554/mgmt/filestore/${entornoSelect}/local/disk/${params.PROJECT}/configuration/${archivo} --header 'Authorization: Basic amVua2luczpqazIwMjQu' --header 'Content-Type: application/json' --data '{\\"file\\": {\\"name\\": \\"${archivo}\\", \\"content\\": \\"${encoded}\\"}}'"
      """
      sleep(10)
     sh """
ssh jenkins@srvcceadpl01 "curl -k --location --request PUT https://dp02usofges:5554/mgmt/filestore/${entornoSelect}/local/disk/${params.PROJECT}/configuration/${archivo} --header 'Authorization: Basic amVua2luczpqazIwMjQu' --header 'Content-Type: application/json' --data '{\\"file\\": {\\"name\\": \\"${archivo}\\", \\"content\\": \\"${encoded}\\"}}'"
      """
}else if(params.DEPLOY  == "EXTRANET"){
      echo "************* EXTRANET"

      sh """
ssh jenkins@srvcceadpl01 "curl -k --location --request PUT https://dp01extrages:5554/mgmt/filestore/${entornoSelect}/local/disk/${params.PROJECT}/configuration/${archivo} --header 'Authorization: Basic amVua2luczpqazIwMjQu' --header 'Content-Type: application/json' --data '{\\"file\\": {\\"name\\": \\"${archivo}\\", \\"content\\": \\"${encoded}\\"}}'"
      """
      sleep(10)
     sh """
ssh jenkins@srvcceadpl01 "curl -k --location --request PUT https://dp02extrages:5554/mgmt/filestore/${entornoSelect}/local/disk/${params.PROJECT}/configuration/${archivo} --header 'Authorization: Basic amVua2luczpqazIwMjQu' --header 'Content-Type: application/json' --data '{\\"file\\": {\\"name\\": \\"${archivo}\\", \\"content\\": \\"${encoded}\\"}}'"
      """
}
}
           
        }
      }
    }
    
    }
    post {
        always {
            cleanWs() 
        }
    }
}

