// http://stackoverflow.com/questions/16963309/how-create-and-configure-a-new-jenkins-job-using-groovy
import jenkins.model.*
import static groovy.io.FileType.FILES

def env = System.getenv()
String repo = env['PROJECT_REPO']
String branch = env['PROJECT_BRANCH']

def proc1 = ['rm','-rf','/job/*'].execute()
proc1.waitFor()
def proc2 = ['/usr/bin/git', 'clone', '-b', branch, repo, '/jobs/'].execute()
proc2.waitFor()

new File('/jobs/').eachFileRecurse(FILES) {
    if(it.name.endsWith('.xml')) {
        def jobName = it.name.replaceAll('.xml','').replaceAll('_',' ')
        String fileContents = new File((String) it).text
        def configXml = fileContents // your xml goes here
        def xmlStream = new ByteArrayInputStream( configXml.getBytes() )
        Jenkins.instance.createProjectFromXML(jobName, xmlStream)

    }
}