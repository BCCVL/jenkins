// http://stackoverflow.com/questions/16963309/how-create-and-configure-a-new-jenkins-job-using-groovy
import jenkins.model.*
import static groovy.io.FileType.FILES

new File('/jobs/').eachFileRecurse(FILES) {
    if(it.name.endsWith('.xml')) {
        def jobName = it.name.replaceAll('.xml','').replaceAll('_',' ')
        String fileContents = new File((String) it).text
        def configXml = fileContents // your xml goes here
        def xmlStream = new ByteArrayInputStream( configXml.getBytes() )
        Jenkins.instance.createProjectFromXML(jobName, xmlStream)

    }
}