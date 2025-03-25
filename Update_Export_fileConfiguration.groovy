def version=""
def entornos=""
pipeline {
    agent any
    environment {
        LDESPLIEGUE = 'ONLY_ONE'
    }
    
    parameters { 
        choice(name: 'PROYECT', choices: ['WSP_ACCEDA','WSP_ACCEDA_CLAVEDEF','WSP_ACCEDA_CLAVEDEF_EXTRANET','WSP_AEAT','WSP_AGT','WSP_API','WSP_ARCHIVA','WSP_BALMIS','WSP_BALMIS_Horus','WSP_BALMIS_Multicita','WSP_CARPETA_CIUDADANO','WSP_CODICE','WSP_CORPORATIVO','WSP_CSN','WSP_CVCDEF','WSP_CVD','WSP_DCORE','WSP_DCORE_anotaREPEMI','WSP_DCORE_anotaREPEREST','WSP_DCORE_DIGITADEF','WSP_DCORE_DIR3DEF','WSP_DCORE_MENSADEF','WSP_DCORE_RCP','WSP_DCORE_RECECUT','WSP_DCORE_RUBRICA','WSP_DCORE_RUBRICAV4','WSP_DCORE_SINFRADEF','WSP_DICODEF_Servicios','WSP_DIGITADEF','WSP_DIR3DEF','WSP_EMS','WSP_DOCUMENTUM','WSP_GEISER','WSP_MAIL','WSP_ISFAS','WSP_MSCBS','WSP_MSCBS_ISFAS','WSP_RECONFAS','WSP_RegistroElectronico','WSP_RUBRICAV4','WSP_SAPROMIL','WSP_SCAN','WSP_SCSP','WSP_SERMAS','WSP_SERMAS_MANTENIMIENTO_CITAS','WSP_SICCOCEN','WSP_SIDAE','WSP_SIGECAR','WSP_SIGLE-Prov','WSP_SIGLE','WSP_SIGLE_EXTERNOS','WSP_SIGMAWEB','WSP_SIPERDEF','WSP_SIPERDEF_ARGO','WSP_SIPERDEF_DCORE','WSP_SIPERDEF_GNOSS','WSP_SIPERDEF_RECECUT','WSP_SIPERDEF_REPEMI','WSP_SIPERDEF_SEDE','WSP_SIPERDEF_SELCON','WSP_SIPERDEF_SICSPSE2','WSP_SIPERDEF_RNA','WSP_SIPERDEF_PORTAL','WSP_SIPERDEF_SIGEI','WSP_SIPERDEF_SISTRAM','WSP_SIPERDEF_TIMEA','WSP_SSRL','WSP_SSRL_ConsingmentService','WSP_TELCON','WSP_TE_EXTRA','WSP_TramitacionEletronica','MPG_AMPS-SLOG','MPG_ACCEDA','MPG_BALANCEO_USOF','MPG_API_HTTP','MPG_CMIS_AUTEN_SSL','MPG_DCORE_ESB','MPG_Publica_WSDL_SERMAS','MPG_SSRL_IN','MPG_Entrada','MPG_DCORE_REPEMI','MPG_DCORE_REPEREST','MPG_DCORE_SOAP','MPG_ISFAS','MPG_ServiciosREST','MPG_SIUCOM','MPG_SECUREBK_SFTP','MPG_SSRL','MPGW-DICOM'], description: 'Select an proyect')
    }
    
   // parameters { 
   //     choice(name: 'PROYECT', choices: ['WSP_ACCEDA','WSP_ACCEDA_CLAVEDEF','WSP_ACCEDA_CLAVEDEF_EXTRANET','WSP_AEAT','WSP_AGT','WSP_API','WSP_ARCHIVA','WSP_BALMIS','WSP_BALMIS_Horus','WSP_BALMIS_Multicita','WSP_CARPETA_CIUDADANO','WSP_CODICE','WSP_CORPORATIVO','WSP_CSN','WSP_CVCDEF','WSP_CVD','WSP_DCORE','WSP_DCORE_anotaREPEMI','WSP_DCORE_MENSADEF','WSP_DCORE_RCP','WSP_DICODEF_Servicios','WSP_DIGITADEF','WSP_DIR3DEF','WSP_EMS','WSP_DOCUMENTUM','WSP_GEISER','WSP_MAIL','WSP_ISFAS','WSP_MSCBS','WSP_MSCBS_ISFAS','WSP_RECONFAS','WSP_RegistroElectronico','WSP_SAPROMIL','WSP_SCAN','WSP_SCSP','WSP_SERMAS','WSP_SERMAS_MANTENIMIENTO_CITAS','WSP_SICCOCEN','WSP_SIDAE','WSP_SIGECAR','WSP_SIGLE-Prov','WSP_SIGLE','WSP_SIGLE_EXTERNOS','WSP_SIGMAWEB','WSP_SIPERDEF','WSP_SIPERDEF_ARGO','WSP_SIPERDEF_DCORE','WSP_SIPERDEF_GNOSS','WSP_SIPERDEF_RECECUT','WSP_SIPERDEF_REPEMI','WSP_SIPERDEF_SEDE','WSP_SIPERDEF_SELCON','WSP_SIPERDEF_SICSPSE2','WSP_SIPERDEF_RNA','WSP_SIPERDEF_PORTAL','WSP_SIPERDEF_SIGEI','WSP_SIPERDEF_SISTRAM','WSP_SIPERDEF_TIMEA','WSP_SSRL','WSP_SSRL_ConsingmentService','WSP_TELCON','WSP_TE_EXTRA','WSP_TramitacionEletronica','MPG_AMPS-SLOG','MPG_ACCEDA','MPG_BALANCEO_USOF','MPG_API_HTTP','MPG_CMIS_AUTEN_SSL','MPG_DCORE_ESB','MPG_Publica_WSDL_SERMAS','MPG_SSRL_IN','MPG_Entrada','MPG_DCORE_REPEMI','MPG_DCORE_REPEREST','MPG_DCORE_SOAP','MPG_ISFAS','MPG_ServiciosREST','MPG_SIUCOM','MPG_SECUREBK_SFTP','MPG_SSRL','MPGW-DICOM'], description: 'Select an proyect')
   // }
    
stages {
	stage ('Descargando Informacion'){
    steps{
    script{	
		//def repo_url = "git@git.servdev.mdef.es:lmunma1/${params.PROYECT}.git"
		def repo_url = "git@git.servdev.mdef.es:sdgplasitel/middleware/${params.PROYECT}.git"
	     echo " ********************************************** "
	     echo " *******Descarga Repositorio Actual************ "
	     echo " ********************************************** "
	    sshagent(['jenkins_Git_SSH_Access']) {
	    sh """
           if [ ! -d "${params.PROYECT}" ]; then
               git clone --branch develop --single-branch ${repo_url}
           fi
        """
	    }
        sleep(20)
        echo " ****************************************** "
	    echo " *******FIN DE LA DESCARGA     ************ "
	    echo " ****************************************** "
    }
    }
    }
	 
    stage ('Update_ConfigurationFile_GIT') {
          steps {
             script {
                println "Datapower_Update_ConfigurationFile"
                selectedOptions = env.LDESPLIEGUE
                appSelect = params.PROYECT
                echo "You selected: ${selectedOptions}"
                
                build job: 'Datapower_Update_ConfigurationFile_GIT', parameters: [
                string(name: 'PARAM1', value: selectedOptions),
                string(name: 'PARAM2', value: "DESA"),
                string(name: 'PARAM3', value: params.PROYECT),
                string(name: 'PARAM4', value: "DIVINDES"),
                string(name: 'PARAM5', value: params.PROYECT),
                string(name: 'PARAM6', value: "DIVINDES"),
				string(name: 'PARAM7', value: "0")
                ]
             }
          }
       }
    }
      post {
        aborted {
            echo "El pipeline ha sido abortado."
            cleanWs() 
            
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
