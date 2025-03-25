pipeline {
    agent any
    environment {
        LDESPLIEGUE = 'ONLY_ONE'
    }
     parameters { 
        choice(name: 'DEPLOY', choices: ['DESA', 'INTRAGES', 'USOOFICIAL', 'EXTRANET'], description: 'Select an option')
        //choice(name: 'PROYECT', choices: ['WSP_ACCEDA','WSP_ACCEDA_CLAVEDEF','WSP_ACCEDA_CLAVEDEF_EXTRANET','WSP_AEAT','WSP_AGT','WSP_API','WSP_ARCHIVA','WSP_BALMIS','WSP_BALMIS_Horus','WSP_BALMIS_Multicita','WSP_CARPETA_CIUDADANO','WSP_CODICE','WSP_CORPORATIVO','WSP_CSN','WSP_CVCDEF','WSP_CVD','WSP_DCORE','WSP_DCORE_anotaREPEMI','WSP_DCORE_MENSADEF','WSP_DCORE_RCP','WSP_DICODEF_Servicios','WSP_DIGITADEF','WSP_DIR3DEF','WSP_EMS','WSP_DOCUMENTUM','WSP_GEISER','WSP_MAIL','WSP_ISFAS','WSP_MSCBS','WSP_MSCBS_ISFAS','WSP_RECONFAS','WSP_RegistroElectronico','WSP_SAPROMIL','WSP_SCAN','WSP_SCSP','WSP_SERMAS','WSP_SERMAS_MANTENIMIENTO_CITAS','WSP_SICCOCEN','WSP_SIDAE','WSP_SIGECAR','WSP_SIGLE-Prov','WSP_SIGLE','WSP_SIGLE_EXTERNOS','WSP_SIGMAWEB','WSP_SIPERDEF','WSP_SIPERDEF_ARGO','WSP_SIPERDEF_DCORE','WSP_SIPERDEF_GNOSS','WSP_SIPERDEF_RECECUT','WSP_SIPERDEF_REPEMI','WSP_SIPERDEF_SEDE','WSP_SIPERDEF_SELCON','WSP_SIPERDEF_SICSPSE2','WSP_SIPERDEF_PORTAL', 'WSP_SIPERDEF_RNA','WSP_SIPERDEF_SIGEI','WSP_SIPERDEF_SISTRAM','WSP_SIPERDEF_TIMEA','WSP_SSRL','WSP_SSRL_ConsingmentService','WSP_TELCON','WSP_TE_EXTRA','WSP_TramitacionEletronica','MPG_AMPS-SLOG','MPG_ACCEDA','MPG_BALANCEO_USOF','MPG_API_HTTP','MPG_CMIS_AUTEN_SSL','MPG_DCORE_ESB','MPG_Publica_WSDL_SERMAS','MPG_SSRL_IN','MPG_Entrada','MPG_DCORE_REPEMI','MPG_DCORE_REPEREST','MPG_DCORE_SOAP','MPG_ISFAS','MPG_ServiciosREST','MPG_SIUCOM','MPG_SECUREBK_SFTP','MPG_SSRL','MPGW-DICOM'], description: 'Select an proyect')
        choice(name: 'PROYECT', choices: ['WSP_ACCEDA','WSP_ACCEDA_CLAVEDEF','WSP_ACCEDA_CLAVEDEF_EXTRANET','WSP_AEAT','WSP_AGT','WSP_API','WSP_ARCHIVA','WSP_BALMIS','WSP_BALMIS_Horus','WSP_BALMIS_Multicita','WSP_CARPETA_CIUDADANO','WSP_CODICE','WSP_CORPORATIVO','WSP_CSN','WSP_CVCDEF','WSP_CVD','WSP_DCORE','WSP_DCORE_anotaREPEMI','WSP_DCORE_anotaREPEREST','WSP_DCORE_DIGITADEF','WSP_DCORE_DIR3DEF','WSP_DCORE_MENSADEF','WSP_DCORE_RCP','WSP_DCORE_RECECUT','WSP_DCORE_RUBRICA','WSP_DCORE_RUBRICAV4','WSP_DCORE_SINFRADEF','WSP_DICODEF_Servicios','WSP_DIGITADEF','WSP_DIR3DEF','WSP_EMS','WSP_DOCUMENTUM','WSP_GEISER','WSP_MAIL','WSP_ISFAS','WSP_MSCBS','WSP_MSCBS_ISFAS','WSP_RECONFAS','WSP_RegistroElectronico','WSP_RUBRICAV4','WSP_SAPROMIL','WSP_SCAN','WSP_SCSP','WSP_SERMAS','WSP_SERMAS_MANTENIMIENTO_CITAS','WSP_SICCOCEN','WSP_SIDAE','WSP_SIGECAR','WSP_SIGLE-Prov','WSP_SIGLE','WSP_SIGLE_EXTERNOS','WSP_SIGMAWEB','WSP_SIPERDEF','WSP_SIPERDEF_ARGO','WSP_SIPERDEF_DCORE','WSP_SIPERDEF_GNOSS','WSP_SIPERDEF_RECECUT','WSP_SIPERDEF_REPEMI','WSP_SIPERDEF_SEDE','WSP_SIPERDEF_SELCON','WSP_SIPERDEF_SICSPSE2','WSP_SIPERDEF_RNA','WSP_SIPERDEF_PORTAL','WSP_SIPERDEF_SIGEI','WSP_SIPERDEF_SISTRAM','WSP_SIPERDEF_TIMEA','WSP_SSRL','WSP_SSRL_ConsingmentService','WSP_TELCON','WSP_TE_EXTRA','WSP_TramitacionEletronica','MPG_AMPS-SLOG','MPG_ACCEDA','MPG_BALANCEO_USOF','MPG_API_HTTP','MPG_CMIS_AUTEN_SSL','MPG_DCORE_ESB','MPG_Publica_WSDL_SERMAS','MPG_SSRL_IN','MPG_Entrada','MPG_DCORE_REPEMI','MPG_DCORE_REPEREST','MPG_DCORE_SOAP','MPG_ISFAS','MPG_ServiciosREST','MPG_SIUCOM','MPG_SECUREBK_SFTP','MPG_SSRL','MPGW-DICOM'], description: 'Select an proyect')
        string(name: 'SCANS', defaultValue: 'vacio', description: 'Insert Scans')
    }
   
stages {
    stage ('Select Domain'){
    steps{
    script{
        def entornos=""
        echo "${env.LDESPLIEGUE}  -- ${params.DEPLOY} ${params.PROYECT} ${params.SCANS}" 
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
        appSelect = params.PROYECT
        echo "You selected: ${selectedOptions}"
    }
    }
    }
	stage ('Input Version'){
    steps{
    script{	
			def INPUT_PARAMS = input(
                id: 'env.VERSION', 
                message: 'Versión a generar', 
                parameters: [
                [$class: 'StringParameterDefinition', defaultValue: '0', description: 'Version Major', name: 'MAJOR'],
                [$class: 'StringParameterDefinition', defaultValue: '0', description: 'Version Minor', name: 'MINOR'],
                [$class: 'StringParameterDefinition', defaultValue: '0', description: 'Version de Hotfix', name: 'PATCH']
                ]);
                
                MAJOR = INPUT_PARAMS.MAJOR;
                MINOR = INPUT_PARAMS.MINOR;
                PATCH = INPUT_PARAMS.PATCH;
                BUILD = "0";

        
        // Save to variables. Default to empty string if not found.
		version = MAJOR + "." + MINOR + "." + PATCH + "." + BUILD
        inputConfig = entornoSelect
        echo "Domain: ${inputConfig}"
        selectedOptions = env.LDESPLIEGUE
        appSelect = params.PROYECT
        echo "You selected: ${selectedOptions}"
		echo ("Versión: "+ MAJOR + "." + MINOR + "." + PATCH + "." + BUILD)
    }
    }
    }
	   stage ('DESA') {
          when{
               anyOf {
                expression { (params.DEPLOY == 'DESA') }
               }
            }
          steps {
             script {
                println "DESA here"
                selectedOptions = env.LDESPLIEGUE
                appSelect = params.PROYECT
                echo "You selected: ${selectedOptions}"
                build job: 'Datapower_Artefacts_DIGOPER_DEV', parameters: [
                string(name: 'PARAM1', value: selectedOptions),
                string(name: 'PARAM2', value: '1'),
                string(name: 'PARAM3', value: params.PROYECT),
                string(name: 'PARAM4', value: inputConfig),
                string(name: 'PARAM5', value: params.PROYECT),
                string(name: 'PARAM6', value: params.SCANS),
				string(name: 'PARAM7', value: version)
                ]
               
                
             }
          }
       }
       stage ('INTRAGES') {
          when{
               anyOf {
                expression { (params.DEPLOY == 'DESA') && (env.LDESPLIEGUE.matches("ALL(.*)")) }
                expression { (params.DEPLOY == 'INTRAGES') }
               }
            }

          steps {
              script {
                 println "INTRAGES Testing here"
                 if (params.DEPLOY == 'DESA'){
                    build job: 'Datapower_Artefacts_DIGOPER_WAN-PG', parameters: [
                    string(name: 'PARAM1', value: selectedOptions),
                    string(name: 'PARAM2', value: '0'),
                    string(name: 'PARAM3', value: params.PROYECT),
                    string(name: 'PARAM4', value: inputConfig),
                    string(name: 'PARAM5', value: params.PROYECT),
                    string(name: 'PARAM6', value: params.SCANS),
					string(name: 'PARAM7', value: version)
                    ]
                 }else{
                     build job: 'Datapower_Artefacts_DIGOPER_WAN-PG', parameters: [
                     string(name: 'PARAM1', value: selectedOptions),
                     string(name: 'PARAM2', value: '1'),
                     string(name: 'PARAM3', value: params.PROYECT),
                     string(name: 'PARAM4', value: inputConfig),
                     string(name: 'PARAM5', value: params.PROYECT),
                     string(name: 'PARAM6', value: params.SCANS),
					 string(name: 'PARAM7', value: version)
                    ]
                 }
              }
          }
       }
       stage('USOOFICIAL') {
          when{
               anyOf {
                expression { (params.DEPLOY == 'DESA')  && (env.LDESPLIEGUE.matches("ALL(.*)")) }
                expression { (params.DEPLOY == 'INTRAGES')  && (env.LDESPLIEGUE.matches("ALL(.*)")) }
                expression { (params.DEPLOY == 'USOOFICIAL') }
               }
            }

          steps {
             script {
                println "PRE Deployment here"
                 if ((params.DEPLOY == 'DESA') || (params.DEPLOY == 'INTRAGES')){
                    build job: 'Datapower_Artefacts_DIGOPER_USO-OFICIAL', parameters: [
                    string(name: 'PARAM1', value: selectedOptions),
                    string(name: 'PARAM2', value: '0'),
                    string(name: 'PARAM3', value: params.PROYECT),
                    string(name: 'PARAM4', value: inputConfig),
                    string(name: 'PARAM5', value: params.PROYECT),
                    string(name: 'PARAM6', value: params.SCANS),
					string(name: 'PARAM7', value: version)
                    ]
                 }else{
                     build job: 'Datapower_Artefacts_DIGOPER_USO-OFICIAL', parameters: [
                     string(name: 'PARAM1', value: selectedOptions),
                     string(name: 'PARAM2', value: '1'),
                     string(name: 'PARAM3', value: params.PROYECT),
                     string(name: 'PARAM4', value: inputConfig),
                     string(name: 'PARAM5', value: params.PROYECT),
                     string(name: 'PARAM6', value: params.SCANS),
					 string(name: 'PARAM7', value: version)
                    ]
                 }
             }
          }
       }
    
     stage('EXTRANET') {
           when{
               anyOf {
                expression { (params.DEPLOY == 'DESA') && (env.LDESPLIEGUE.matches("ALL(.*)")) }
                expression { (params.DEPLOY == 'INTRAGES')  && (env.LDESPLIEGUE.matches("ALL(.*)")) }
                expression { (params.DEPLOY == 'USOOFICIAL')  && (env.LDESPLIEGUE.matches("ALL(.*)")) }
                expression { (params.DEPLOY == 'EXTRANET') }
               }
            }
          steps {
             script {
                 
                println "PRO Deployment here"
                 if ((params.DEPLOY == 'DESA') || (params.DEPLOY == 'INTRAGES') || (params.DEPLOY == 'USOOFICIAL')){
                    build job: 'Datapower_Artefacts_DIGOPER_EXTRANET', parameters: [
                    string(name: 'PARAM1', value: selectedOptions),
                    string(name: 'PARAM2', value: '0'),
                    string(name: 'PARAM3', value: params.PROYECT),
                    string(name: 'PARAM4', value: inputConfig),
                    string(name: 'PARAM5', value: params.PROYECT),
                    string(name: 'PARAM6', value: params.SCANS),
					string(name: 'PARAM7', value: version)
                    ]
                 }else{
                     build job: 'Datapower_Artefacts_DIGOPER_EXTRANET', parameters: [
                     string(name: 'PARAM1', value: selectedOptions),
                     string(name: 'PARAM2', value: '1'),
                     string(name: 'PARAM3', value: params.PROYECT),
                     string(name: 'PARAM4', value: inputConfig),
                     string(name: 'PARAM5', value: params.PROYECT),
                     string(name: 'PARAM6', value: params.SCANS),
					 string(name: 'PARAM7', value: version)
                    ]
                 }
                
             }
          }
       }
    }
	 post {
        always {
            echo 'limpiar WS.'
            cleanWs()  // Limpia el workspace del nodo donde se ejecutó el pipeline
        }
        success {
            echo 'SUCCESS: El pipeline fue exitoso.'
        }
        failure {
            echo 'ERROR: El pipeline falló.'
        }
    }
}
============================================================
  DATAPOWER_ARTEFACTS_DIGOPER_DEV (Datapower_Artefacts_DIGOPER_DEV)
========================================================
  def proyectSelect=""
def pathWS=""
def ficheroZip2=""
node {
  try{
    env.MAQUINA = "srvcceadpl02"
    
    parameters {
        string(name: 'PARAM1', defaultValue: '', description: 'Primer parámetro')
        string(name: 'PARAM2', defaultValue: '', description: 'Segundo parámetro')
        string(name: 'PARAM3', defaultValue: '', description: 'tercer parámetro')
        string(name: 'PARAM4', defaultValue: '', description: 'cuarto parámetro')
        string(name: 'PARAM5', defaultValue: '', description: 'quinto parámetro')
        string(name: 'PARAM6', defaultValue: '', description: 'sexto parámetro')
		string(name: 'PARAM7', defaultValue: '', description: 'septimo parámetro')
		
        
    }
    pathWS = pwd()	
    echo "****** ${params.PARAM1}"
    echo "****** ${params.PARAM2}"
    echo "****** ${params.PARAM3}"
    echo "****** ${params.PARAM4}"
    echo "****** ${params.PARAM5}"
    echo "****** ${params.PARAM6}"
	echo "****** ${params.PARAM7}"

    stage "Download nexus - Zip"
	ficheroZip2 = params.PARAM3 + ".zip"
	 echo " *****Inicio descarga  ${params.PARAM3}  --- ${ficheroZip2} ****** "
    withCredentials([usernamePassword(credentialsId: 'jenkins120_nexus', passwordVariable: 'claveDBProperties', usernameVariable: 'usuarioDBProperties')]){
        try{ 
           echo " ****** PROCESO VERIFICACION EXISTE ARTEFACTO *********** "
            
		   command_curl_verifica = "curl -I -v -k -u " + usuarioDBProperties + ":" + claveDBProperties + " https://nexus.servdev.mdef.es/repository/SDGPLASITEL-DATAPOWER/${params.PARAM3}/${params.PARAM7}/${ficheroZip2}"
           exec_verifica = "${command_curl_verifica}"
          output_verifica = sh(script: exec_verifica, returnStdout: true)
          echo "==== ${output_verifica}"
          if (output_verifica.toString().contains("200")) {
              echo " *****la version  existe en Nexus  ****** "
          } else {
               echo " *****ERROR: la version no existe en Nexus  ****** "
            currentBuild.result = 'ABORTED'
            throw new hudson.AbortException('')
          }
         
		  sleep(20)
          echo " *****fin Verifica /DESCARGA ****** "
		   
           		   
           echo " ***** PROCESO DE DESCARGA DE ARTEFACTOS  ****** "
          command_curl_descarga = "curl -v -k -u " + usuarioDBProperties + ":" + claveDBProperties + " -o ${pathWS}/${ficheroZip2}  " + " https://nexus.servdev.mdef.es/repository/SDGPLASITEL-DATAPOWER/${params.PARAM3}/${params.PARAM7}/${ficheroZip2}"
          
          exec_descarga = "${command_curl_descarga}"
          output = sh(script: exec_descarga, returnStdout: true)
		  sleep(20)
	
          echo " *****fin descarga  ****** "
        }catch(err){
           echo " *****ERROR : PROCESO DE DESCARGA DE ARTEFACTOS  ****** "
            currentBuild.result = 'ABORTED'
            throw new Exception("ERROR : PROCESO DE DESCARGA DE ARTEFACTOS")
        }
      }

    sleep(10)
    
    echo " ************ final Descarga ************ "
    
    stage "Archive build output"
    sleep(15)
    sh "scp -v ${pathWS}/${ficheroZip2}  jenkins@${env.MAQUINA}:/home/jenkins/deployDP/project_deploy"
    
    echo " ************ Pasando fichero configuracion ************ "
    echo " --> ${params.PARAM4} ${params.PARAM5} ${params.PARAM6}"
    sleep(15)
    sh "sudo ssh jenkins@${env.MAQUINA} 'cd /home/jenkins/deployDP && ls'"
    sleep(5)
    //sh "sudo ssh jenkins@${env.MAQUINA} 'cd /home/jenkins/deployDP && ./deploy_dp.sh amVua2luczpqazIwMjQu srvcceadtpwl01d 5554 ${params.PARAM4} ${params.PARAM5} ${params.PARAM6}'"
     sh "ssh jenkins@${env.MAQUINA} 'cd /home/jenkins/deployDP && ./deploy_dp.sh amVua2luczpqazIwMjQu srvcceadtpwl01d 5554 ${params.PARAM4} ${params.PARAM5} ${params.PARAM6}'"
    sleep(10)
    echo " ************ final de la ejecucion ************ "
  }finally {
      echo "***** Borrando Workspace ******"
      cleanWs()  // Limpia el workspace del nodo donde se ejecutó el pipeline
  }  
}
