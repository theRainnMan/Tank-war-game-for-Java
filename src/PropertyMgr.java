import java.io.IOException;
import java.util.Properties;

/*public class PropertyMgr {
	public static String getProperty(String key){
		Properties props = new Properties();
		try {
			props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props.getProperty(key);
	}
}
*/


public class PropertyMgr{
	static Properties props = new Properties();
	static{
		try {
			props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getProperty(String key){
		return props.getProperty(key);
	}
}
