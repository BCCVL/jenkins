// http://stackoverflow.com/questions/16963309/how-create-and-configure-a-new-jenkins-job-using-groovy
import jenkins.model.*

def jobName = "my-new-job"
def configXml = "" // your xml goes here

def xmlStream = new ByteArrayInputStream( configXml.getBytes() )

Jenkins.instance.createProjectFromXML(jobName, xmlStream)