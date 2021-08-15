package support;

import java.io.File;

public class Settings {
	private static Settings settings;
	private String projectPath;
	private String configurationProp;
	private String dataSource;
	private String dataXMLSource;
	private String resourcesDir;
	private String driverEXEDir;
	private String screenshotsDir;
	private String downloadDir;
	private Settings(){};

	public static Settings getInstance(){
		if(settings==null){
			settings= new Settings();
		}
		return settings;
	}

	public void setProjectPath(){
		String projectPath = new File(System.getProperty("user.dir")).getAbsolutePath();
		this.projectPath=projectPath;
	}

	public String getProjectPath() {
		if(projectPath==null){
			setProjectPath();
		}
		return projectPath;
	}

	public String getConfigurationProp() {
		if(configurationProp==null){
			setConfigurationProp();
		}
		return configurationProp;
	}

	public void setConfigurationProp() {
		this.configurationProp = getProjectPath()+System.getProperty("file.separator")+"configurations.properties";
	}

	public String getDataSource() {
		if(dataSource==null){
			setDataSource();
		}
		return dataSource;
	}

	public void setDataSource() {
		this.dataSource = getProjectPath()+System.getProperty("file.separator")+"resources"+System.getProperty("file.separator");
	}

	public String getXMLDataSource() {
		if(dataXMLSource==null){
			setXMLDataSource();
		}
		return dataXMLSource;
	}

	public void setXMLDataSource() {
		this.dataXMLSource = getProjectPath()+System.getProperty("file.separator")+"resources"+System.getProperty("file.separator")+"XmlSources"+System.getProperty("file.separator");
	}

	public String getResourcesDir() {
		if(resourcesDir==null){
			setResourcesDir();
		}
		return resourcesDir;
	}

	public void setResourcesDir() {
		this.resourcesDir = getProjectPath()+System.getProperty("file.separator")+"resources"+System.getProperty("file.separator");
	}

	public String getDriverEXEDir() {
		if(driverEXEDir==null){
			setDriverEXEDir();
		}
		return driverEXEDir;
	}

	public void setDriverEXEDir() {
		this.driverEXEDir = getProjectPath()+System.getProperty("file.separator")+"resources"+System.getProperty("file.separator")+"driver-exe"+System.getProperty("file.separator");
	}

	public String getScreenshotsDir() {
		if(screenshotsDir==null){
			setScreenshotsDir();
		}
		return screenshotsDir;
	}

	public void setScreenshotsDir() {
		this.screenshotsDir = getProjectPath()+System.getProperty("file.separator")+"screenshots"+System.getProperty("file.separator");
	}
	
	public void setDownloadFolder() {
		this.downloadDir = getProjectPath()+System.getProperty("file.separator")+"downloads"+System.getProperty("file.separator");
	}
	
	public String getDownloadFolder() {
		if(downloadDir==null){
			setDownloadFolder();
		}
		return downloadDir;
	}
}
