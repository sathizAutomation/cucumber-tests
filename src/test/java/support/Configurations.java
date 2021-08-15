package support;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {
	private static Configurations configurations;
	private Properties properties;
	private Settings settings = Settings.getInstance();
	private Configurations(){};
	public static Configurations getInstance(){
		if(configurations==null){
			configurations= new Configurations();
		}
		return configurations;
	}

	private void setConfigurationProperties(){
		String confFile = settings.getConfigurationProp();
		properties = new Properties();
		try {
			InputStream inStream = new FileInputStream(confFile);
			properties.load(inStream);
		} catch (Exception e) {
			new Exception("Error in reading configuration file!!!");
		}
	}

	public String getProperty(String property){
		if(properties==null){
			setConfigurationProperties();
		}
		if(properties.getProperty(property)!=null){
			return properties.getProperty(property);
		}else{
			return "No such property";
		}
	}
}