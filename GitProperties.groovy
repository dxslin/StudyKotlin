class GitProperties {
    private Properties publicProperties
    private Properties localProperties

    GitProperties(Project project, String publicFile, String localFile) {
        publicProperties = new Properties()
        publicProperties.load(project.file(publicFile).newDataInputStream())
        localProperties = new Properties()
        localProperties.load(project.file(localFile).newDataInputStream())
    }

    static String or(String self, String another) {
        return self == null || self.isEmpty() ? another : self
    }

    String getValue(String key) {
        return or(publicProperties.getProperty(key), localProperties.getProperty(key))
    }
}