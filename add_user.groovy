// Based on https://gist.github.com/hayderimran7/50cb1244cc1e856873a4
import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("jenkins","secr3t")
instance.setSecurityRealm(hudsonRealm)

def strategy = new GlobalMatrixAuthorizationStrategy()
strategy.add(Jenkins.ADMINISTER, "jenkins")
instance.setAuthorizationStrategy(strategy)

instance.save()