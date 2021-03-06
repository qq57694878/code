package com.youi.util.po;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 *  
 * 
 * @version 2008-4-29
 * 
 */
public class CreateDaoLjl
{
	private ResourceLoader resourceLoader = new DefaultResourceLoader();
	public CreateDaoLjl(){
	/*	ResourceBundle bundle =	ResourceBundle.getBundle("createModel");
		driverClassName = bundle.getString("driverClassName");
		dbURL = bundle.getString("url");
		dbUser = bundle.getString("username");
		dbPass=bundle.getString("password");
		pack = bundle.getString("package");
		sourcePackage = bundle.getString("sourcePackage");
		String tableStr = bundle.getString("tables");*/
		Resource rs =resourceLoader.getResource(confFile);
		Properties prop = new Properties() ;
		try {
			prop.load(rs.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		driverClassName = prop.getProperty("driverClassName");
		dbURL = prop.getProperty("url");
		tableSchema = dbURL.substring(dbURL.lastIndexOf("/")+1);
		dbUser = prop.getProperty("username");
		dbPass=prop.getProperty("password");
		pack = prop.getProperty("package");
		sourcePackage = prop.getProperty("sourcePackage");
		String tableStr = prop.getProperty("tables");
		List<String> list = new ArrayList<String>();
		for(String tableName:tableStr.split(",")){
			if(tableName!=null&&tableName.trim().length()>0){
				list.add(tableName);
			}
		}
		tNames = list.toArray(new String[]{});
	}
	private final  String  confFile = "classpath:createModel.properties";
	
	private String driverClassName;
	
	private String  pack;

	private String[] tNames;

	private String dbURL;
	
	private String tableSchema;

	private String dbUser = "giis";

	private String dbPass = "orcl";
	
	private String sourcePackage="src";

	public static void main(String[] args) throws Exception
	{

		CreateDaoLjl create = new CreateDaoLjl();
		create.createModel();
	}
	public void createModel() throws Exception
	{

		String modelPack = pack;
		List tableList = getTableNames(Arrays.asList(tNames));
		for (int i = 0; i < tableList.size(); i++)
		{
			printModel((Table) tableList.get(i), modelPack);
		}
	}
	
	

	public  List getTableNames(List tNames) throws Exception
	{
		Connection con = connectToOracleDb();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select TABLE_NAME,TABLE_COMMENT from information_schema.TABLES where 1=1  and TABLE_SCHEMA='"+tableSchema+"' ");
		sqlBuf.append(" and (1=2 ");
		for (int i = 0; i < tNames.size(); i++)
		{
			sqlBuf.append(" or TABLE_NAME like '" + ((String) tNames.get(i)).toUpperCase() + "' ");
		}
		sqlBuf.append(")");
		System.out.println("---" + sqlBuf.toString());
		pstmt = con.prepareStatement(sqlBuf.toString());
		rs = pstmt.executeQuery();
		ArrayList list = new ArrayList();
		while (rs.next())
		{
			Table table = new Table();
			table.setName(rs.getString(1));
			table.setComment(rs.getString(2));
			list.add(table);
		}
		return list;
	}

	

	public  void printModel(Table table, String pack)
	{
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("package " + pack + ";\n");
		strBuf.append("\n");
		strBuf.append("\n");

		if (table.getComment() != null)
		{
			strBuf.append("	/**\n");
			strBuf.append("	 *" + table.getComment() + "\n");
			strBuf.append("	 */\n");
		}
		else
		{

		}

		strBuf.append("\n");
		strBuf.append("import com.youi.core.hibernate.HibernateEntityDao;\n");
		strBuf.append("import org.springframework.stereotype.Repository;\n");
		strBuf.append("import " + pack + "."+table.getName().toUpperCase()+";\n");
		strBuf.append("@Repository\n");
		String tableName = table.getName().toLowerCase();
		String names[] = tableName.split("_");
		String className="";
		for(String name:names){
			className+=(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
		}
		className+="Dao";
		strBuf.append("public class " +className+ " extends HibernateEntityDao<"+table.getName().toUpperCase()+">\n");
		strBuf.append("{\n");

		strBuf.append("}\n");

		String path = System.getProperty("user.dir");
		if(!path.endsWith("/")){
			path = path+"/";
		}
		String fileFoldName = path+sourcePackage+"/" + pack.replace('.', '/') + "/" + className+ ".java";
		saveToFile(strBuf.toString(), fileFoldName);
	}


	public  void saveToFile(String fileContent, String fileName)
	{

		try
		{
			//FileWriter fw = new FileWriter(new File(fileName));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName)),"UTF-8"));
			bw.write(fileContent);
			bw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println(fileName);
	}

	public  Connection connectToOracleDb() throws Exception
	{
		Class.forName(driverClassName);
		Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
		return conn;
	}


	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public String[] getTNames() {
		return tNames;
	}
	public void setTNames(String[] names) {
		tNames = names;
	}
	public String getDbURL() {
		return dbURL;
	}
	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPass() {
		return dbPass;
	}
	public void setDbPass(String dbPass) {
		this.dbPass = dbPass;
	}
	public String getSourcePackage() {
		return sourcePackage;
	}
	public void setSourcePackage(String sourcePackage) {
		this.sourcePackage = sourcePackage;
	}
}
