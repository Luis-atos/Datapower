def version=""
pipeline {
    agent any
    environment {
        LDESPLIEGUE = 'ONLY_ONE'
    }
    
    parameters { 
        choice(name: 'DEPLOY', choices: ['DESA', 'INTRAGES', 'USOOFICIAL', 'EXTRANET'], description: 'Select an option')
        //choice(name: 'PROYECT', choices: ['WSP_ACCEDA','WSP_ACCEDA_CLAVEDEF','WSP_ACCEDA_CLAVEDEF_EXTRANET','WSP_AEAT','WSP_AGT','WSP_API','WSP_ARCHIVA','WSP_BALMIS','WSP_BALMIS_Horus','WSP_BALMIS_Multicita','WSP_CARPETA_CIUDADANO','WSP_CODICE','WSP_CORPORATIVO','WSP_CSN','WSP_CVCDEF','WSP_CVD','WSP_DCORE','WSP_DCORE_anotaREPEMI','WSP_DCORE_MENSADEF','WSP_DCORE_RCP','WSP_DICODEF_Servicios','WSP_DIGITADEF','WSP_DIR3DEF','WSP_EMS','WSP_DOCUMENTUM','WSP_GEISER','WSP_MAIL','WSP_ISFAS','WSP_MSCBS','WSP_MSCBS_ISFAS','WSP_RECONFAS','WSP_RegistroElectronico','WSP_SAPROMIL','WSP_SCAN','WSP_SCSP','WSP_SERMAS','WSP_SERMAS_MANTENIMIENTO_CITAS','WSP_SICCOCEN','WSP_SIDAE','WSP_SIGECAR','WSP_SIGLE-Prov','WSP_SIGLE','WSP_SIGLE_EXTERNOS','WSP_SIGMAWEB','WSP_SIPERDEF','WSP_SIPERDEF_ARGO','WSP_SIPERDEF_DCORE','WSP_SIPERDEF_GNOSS','WSP_SIPERDEF_RECECUT','WSP_SIPERDEF_REPEMI','WSP_SIPERDEF_SEDE','WSP_SIPERDEF_SELCON','WSP_SIPERDEF_SICSPSE2','WSP_SIPERDEF_RNA','WSP_SIPERDEF_PORTAL','WSP_SIPERDEF_SIGEI','WSP_SIPERDEF_SISTRAM','WSP_SIPERDEF_TIMEA','WSP_SSRL','WSP_SSRL_ConsingmentService','WSP_TELCON','WSP_TE_EXTRA','WSP_TramitacionEletronica','MPG_AMPS-SLOG','MPG_ACCEDA','MPG_BALANCEO_USOF','MPG_API_HTTP','MPG_CMIS_AUTEN_SSL','MPG_DCORE_ESB','MPG_Publica_WSDL_SERMAS','MPG_SSRL_IN','MPG_Entrada','MPG_DCORE_REPEMI','MPG_DCORE_REPEREST','MPG_DCORE_SOAP','MPG_ISFAS','MPG_ServiciosREST','MPG_SIUCOM','MPG_SECUREBK_SFTP','MPG_SSRL','MPGW-DICOM'], description: 'Select an proyect')
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
            parameters: [choice(name: 'Elección Entorno', choices: "${entornos}", description: '¿En cual entorno deseas exportar?')])
	
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
	   stage ('Export_File'){
       steps{
       script{
			    println "Export_File"
                selectedOptions = env.LDESPLIEGUE
                appSelect = params.PROYECT
                echo "You selected: ${selectedOptions}"
                build job: 'Datapower_Export_File', parameters: [
                string(name: 'PARAM1', value: params.DEPLOY),
                string(name: 'PARAM2', value: '1'),
                string(name: 'PARAM3', value: params.PROYECT),
                string(name: 'PARAM4', value: inputConfig),
                string(name: 'PARAM5', value: params.PROYECT),
                string(name: 'PARAM6', value: params.SCANS)
                ]
	   }
	   }
	   }
	   stage ('Upload Nexus') {
          when{
               anyOf {
                expression { (params.DEPLOY == 'DESA') }
                expression { (params.DEPLOY == 'INTRAGES') }
                expression { (params.DEPLOY == 'USOOFICIAL') }
                expression { (params.DEPLOY == 'EXTRANET') }
               }
            }
          steps {
             script {
                println "Upload Nexus Artefact"
                selectedOptions = env.LDESPLIEGUE
                appSelect = params.PROYECT
                echo "You selected: ${selectedOptions}"
                
                build job: 'Datapower_artefact_nexus', parameters: [
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
	   stage ('Update_Gitlab') {
          when{
               anyOf {
                expression { (params.DEPLOY == 'DESA') }
                expression { (params.DEPLOY == 'INTRAGES') }
                expression { (params.DEPLOY == 'USOOFICIAL') }
                expression { (params.DEPLOY == 'EXTRANET') }
               }
            }
          steps {
             script {
                println "Datapower_Update_GIT"
                selectedOptions = env.LDESPLIEGUE
                appSelect = params.PROYECT
                echo "You selected: ${selectedOptions}"
                
                build job: 'Datapower_Update_GIT', parameters: [
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
      post {
        aborted {
            echo "El pipeline ha sido abortado."
            sh "sudo ssh jenkins@srvcceadpl02 'cd /home/jenkins/exportDP/export_object && rm -f ${params.PROYECT}*.zip'"
            
        }
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

===========================
  datapower_ExportFile.groovy
===============================
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
        
    }
    pathWS = pwd()	
    echo "****** ${params.PARAM1}"
    echo "****** ${params.PARAM2}"
    echo "****** ${params.PARAM3}"
    echo "****** ${params.PARAM4}"
    echo "****** ${params.PARAM5}"
    echo "****** ${params.PARAM6}"

    def webservice = params.PARAM3
    def clase = webservice.split('_')[0]
    if (params.PARAM1 == "DESA"){
        entornoRed ="DESARROLLO"
    }else if (params.PARAM1 == "EXTRANET"){
        entornoRed ="EXTRANET"
    }else if (params.PARAM1 == "USOOFICIAL"){
        entornoRed ="USOF"
    }else if (params.PARAM1 == "INTRAGES"){
        entornoRed ="WANPG"
    }
   // sh "./export_object.sh EXPORT ${clase} ${webservice} ${entornoRed} ${params.PARAM4}"
    sh "sudo ssh jenkins@${env.MAQUINA} 'cd /home/jenkins/exportDP && ./export_object.sh ${clase} ${webservice} ${entornoRed} ${params.PARAM4}'"
    //sh "./export_object.sh EXPORT ${clase} ${entornoRed} ${params.PARAM4} ${webservice}"
    echo " ************ final de la ejecucion ${webservice}   ${clase} ************ "
	}finally {
        echo "***** Borrando Workspace ******"
        cleanWs()  // Limpia el workspace del nodo donde se ejecutó el pipeline
    }  
}
========================================
  Datapower_artefacts_Nexus
======================================
  import groovy.json.JsonSlurper

def proyectSelect=""
def pathWS=""
def ficheroZip2=""
node {
	try{
    parameters {
        string(name: 'PARAM1', defaultValue: '', description: 'Primer parámetro')
        string(name: 'PARAM2', defaultValue: '', description: 'Segundo parámetro')
        string(name: 'PARAM3', defaultValue: '', description: 'tercer parámetro')
        string(name: 'PARAM4', defaultValue: '', description: 'cuarto parámetro')
        string(name: 'PARAM5', defaultValue: '', description: 'quinto parámetro')
        string(name: 'PARAM6', defaultValue: '', description: 'sexto parámetro')
		string(name: 'PARAM7', defaultValue: '', description: 'version parámetro')
        
    }
    pathWS = pwd()	
    echo "***** ${pathWS}******"
    def webservice = params.PARAM3
    echo "****** ${params.PARAM1}"
    echo "****** ${params.PARAM2}"
    echo "****** ${params.PARAM3}"
    echo "****** ${params.PARAM4}"
    echo "****** ${params.PARAM5}"
    echo "****** ${params.PARAM6}"
	echo "****** ${params.PARAM7}"

   
    ficheroZip2 = webservice + ".zip"
   
    sleep(10)
    withCredentials([usernamePassword(credentialsId: 'jenkins120_nexus', passwordVariable: 'claveDBProperties', usernameVariable: 'usuarioDBProperties')]) {
	try{
	     sh "sudo ssh jenkins@srvcceadpl02 'cd /home/jenkins/exportDP/export_object && rm -f ${ficheroZip2}'"
	    // sh "sudo ssh jenkins@srvcceadpl02 'cd /home/jenkins/exportDP/export_object && mv -b ${webservice}*.zip ${ficheroZip2}'"
	     sh "sudo ssh jenkins@srvcceadpl02 'cd /home/jenkins/exportDP/export_object && cp ${webservice}*.zip ${ficheroZip2}'"
	
		 echo "***** Entrada a Borrado ***"
		 command_curl_borrado = "curl --request DELETE -u '${usuarioDBProperties}:${claveDBProperties}' https://nexus.servdev.mdef.es/repository/SDGPLASITEL-DATAPOWER/${params.PARAM3}/${params.PARAM7}/${ficheroZip2}"
	     exec_borrando = "${command_curl_borrado}"
	     output = sh(script: exec_borrando, returnStdout: true)
		        echo " *****fin borrado  ****** "
		        sleep(10)
    
	//	}
	echo " ********* Control de Versiones en nexus ************* "
	sh "cd /var/lib/jenkins && python3 nexus_VersionList.py ${params.PARAM3} 'SDGPLASITEL-DATAPOWER' ${usuarioDBProperties} ${claveDBProperties}"
	echo " ********* FIN Control de Versiones en nexus ************* "
	
	echo " *****Proceso Subida  ****** "
	command_curl = "curl -v -k -u " + usuarioDBProperties + ":" + claveDBProperties + " --upload-file ${ficheroZip2}  " + " https://nexus.servdev.mdef.es/repository/SDGPLASITEL-DATAPOWER//${params.PARAM3}/${params.PARAM7}/${ficheroZip2}"  
	exec_subida = "unset HTTPS_PROXY; ${command_curl}"
    sh "sudo ssh jenkins@srvcceadpl02 'cd /home/jenkins/exportDP/export_object && ${exec_subida}'"
	echo " *****aterriza ARTEFACTO  ****** "
	sleep(20)
	}catch(err){
        echo " *****ERROR : PROCESO DE SUBIDA DE ARTEFACTOS  ****** "
        sh "sudo ssh jenkins@srvcceadpl02 'cd /home/jenkins/exportDP/export_object && rm -f ${webservice}*.zip'"
        sleep(10)
		currentBuild.result = 'ABORTED'
		throw new Exception("ERROR : PROCESO DE SUBIDA DE ARTEFACTOS")
	}
    }
    echo " ************ final de la Subida a Nexus ${ficheroZip2}    ************ "
    sh "sudo ssh jenkins@srvcceadpl02 'cd /home/jenkins/exportDP/export_object && rm -f ${ficheroZip2}'"
    sh "sudo ssh jenkins@srvcceadpl02 'cd /home/jenkins/exportDP/export_object && rm -f ${webservice}*.zip'"
	
    }finally {
        echo "***** Borrando Workspace ******"
        cleanWs()  // Limpia el workspace del nodo donde se ejecutó el pipeline
    }  
    
}
==============================
  Datapower_update_GIT
========================
  import groovy.json.JsonSlurper

def proyectSelect=""
def pathWS=""
def ficheroZip2=""
boolean ignoreDiffs = true 
node {
	try{
    parameters {
        string(name: 'PARAM1', defaultValue: '', description: 'Primer parámetro')
        string(name: 'PARAM2', defaultValue: '', description: 'Segundo parámetro')
        string(name: 'PARAM3', defaultValue: '', description: 'tercer parámetro')
        string(name: 'PARAM4', defaultValue: '', description: 'cuarto parámetro')
        string(name: 'PARAM5', defaultValue: '', description: 'quinto parámetro')
        string(name: 'PARAM6', defaultValue: '', description: 'sexto parámetro')
		string(name: 'PARAM7', defaultValue: '', description: 'version parámetro')
        
    }
    pathWS = pwd()	
    echo "***** ${pathWS}******"
    def webservice = params.PARAM3
    echo "****** ${params.PARAM1}"
    echo "****** ${params.PARAM2}"
    echo "****** ${params.PARAM3}"
    echo "****** ${params.PARAM4}"
    echo "****** ${params.PARAM5}"
    echo "****** ${params.PARAM6}"
	echo "****** ${params.PARAM7}"

   
    ficheroZip2 = webservice + ".zip"
   
    sleep(10)
    withCredentials([usernamePassword(credentialsId: 'jenkins120_nexus', passwordVariable: 'claveDBProperties', usernameVariable: 'usuarioDBProperties')]) {
         try{  
           echo " ***** PROCESO DE DESCARGA DE ARTEFACTOS  ****** "
          command_curl_descarga = "curl -v -k -u " + usuarioDBProperties + ":" + claveDBProperties + " -o ${pathWS}/${ficheroZip2}  " + " https://nexus.servdev.mdef.es/repository/SDGPLASITEL-DATAPOWER/${webservice}/${params.PARAM7}/${ficheroZip2}"
          echo " *****${command_curl_descarga} ****** "
          exec_descarga = "${command_curl_descarga}"
          output = sh(script: exec_descarga, returnStdout: true)
          echo " *****fin descarga  ****** "
          sleep(20)
        }catch(err){
           echo " *****ERROR : PROCESO DE DESCARGA DE ARTEFACTOS  ****** "
            currentBuild.result = 'ABORTED'
            throw new Exception("ERROR : PROCESO DE DESCARGA DE ARTEFACTOS")
        }
	    echo " **************** FIN DESCARGA ZIP *****************"
	    def repo_url = "git@git.servdev.mdef.es:sdgplasitel/middleware/${webservice}.git"
	     echo " ********************************************** "
	     echo " *******Descarga Repositorio Actual************ "
	     echo " ********************************************** "
	    
	    sh """
           if [ -d "${webservice}" ]; then
               echo "Existe"
               rm -rf /var/lib/jenkins/workspace/Datapower_Update_GIT/${webservice}
           fi
        """
         sshagent(['jenkins_Git_SSH_Access']) {
            sh "git clone --branch develop --single-branch ${repo_url} ${webservice}"
         }
        sleep(20)
        echo " ********************************************** "
	    echo " ******* Descarga Artefacto Nuevo  ************ "
	    echo " ********************************************** "
        sh """
           ls -la ${webservice}
           unzip -o ${ficheroZip2} -d ${webservice}_new
        """
        sleep(5)
        echo " ******************************************************* "
	    echo " ******* Comparar Git / ZIP (Diferencias) ************** "
	    echo " ******************************************************* "
	    exec_diff = "sudo diff -rq --exclude='.*' ${pathWS}/${webservice}_new ${pathWS}/${webservice}"
        output_exec_diff = sh(script: exec_diff, returnStatus: true)
        echo "--> salida output ... ${output_exec_diff}"
        if (output_exec_diff ==  0){
            echo " *************************************************************** "
	        echo " ******* No hay Diferencias entre repositorio y ZIP ************ "
	        echo " *************************************************************** "
            echo "--> No hay diferencias salida output ..."
        }else{
          if (ignoreDiffs){
            echo "Se ignorarán las diferencias y el pipeline continuará."
    
            echo " ******************************************************** "
	        echo " ******* Diferencias entre repositorio y ZIP ************ "
	        echo " ******************************************************** "
            echo "--> salida output ... ${output_exec_diff}"
            // rsync -av --ignore-existing ${pathWS}/${webservice}_new/ ${pathWS}/${webservice}/
            sh """
               rsync -av ${pathWS}/${webservice}_new/ ${pathWS}/${webservice}/
            """
            sleep(5)
            echo " ******************************************************* "
	        echo " ******* Actualiza Repositorio Git *********************** "
	        echo " ******************************************************* "
	        sshagent(['jenkins_Git_SSH_Access']) {
	        sh """
                cd ${pathWS}/${webservice}
                git add .
                git status
                git commit --allow-empty -m "${webservice} a version ${params.PARAM7}"
                git push origin develop
            """
	        }
          }
        }
       
	    echo " **** git@git.servdev.mdef.es:sdgplasitel/middleware/${webservice}.git" 
	    echo " ****  FIN ********************************* "
    }
    }finally {
        echo "***** Actualizado Repositorio GIT ******"
         sleep(10)
         cleanWs()
    }
}	
