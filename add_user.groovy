// Based on https://gist.github.com/hayderimran7/50cb1244cc1e856873a4
import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

def env = System.getenv()
String username = env['JENKINS_USERNAME']
String password = env['JENKINS_PASSWORD']

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount(username,password)
instance.setSecurityRealm(hudsonRealm)

def strategy = new GlobalMatrixAuthorizationStrategy()
strategy.add(Jenkins.ADMINISTER, username)
instance.setAuthorizationStrategy(strategy)

instance.save()