package com.youi.util.po;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
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
public class CreateSqlLjl
{
	private ResourceLoader resourceLoader = new DefaultResourceLoader();
	public CreateSqlLjl(){
	/*	ResourceBundle bundle =	ResourceBundle.getBundle("createModel");
		driverClassName = bundle.getString("driverClassName");
		dbURL = bundle.getString("url");
		dbUser = bundle.getString("username");
		dbPass=bundle.getString("password");
		pack = bundle.getString("package");
		sourcePackage = bundle.getString("sourcePackage");
		String tableStr = bundle.getString("tables");*/
		Resource resource =resourceLoader.getResource(confFile);
		Properties prop = new Properties() ;
		try {
			prop.load(resource.getInputStream());
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

		CreateSqlLjl create = new CreateSqlLjl();
		create.createModel();
	}
	public void createModel() throws Exception
	{

		List tableList = getTableNames(Arrays.asList(tNames));
		for (int i = 0; i < tableList.size(); i++)
		{
			createSql((Table) tableList.get(i),"T1");
		}
	}
	
	

	public  List getTableNames(List tNames) throws Exception
	{
		Connection con = connectToOracleDb();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select UPPER(TABLE_NAME),TABLE_COMMENT from information_schema.TABLES where 1=1 and TABLE_SCHEMA='"+tableSchema+"'");
		sqlBuf.append(" and (1=2 ");
		for (int i = 0; i < tNames.size(); i++)
		{
			sqlBuf.append(" or TABLE_NAME like '" + ((String) tNames.get(i)).toUpperCase() + "' ");
		}
		sqlBuf.append(")");
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

	public  void createSql(Table table,String t1) throws Exception
	{
		Connection con = connectToOracleDb();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer strBuf = new StringBuffer();

		strBuf.append(" select distinct(t1.COLUMN_NAME),t1.COLUMN_COMMENT,t1.DATA_TYPE from information_schema.COLUMNS t1 WHERE t1.table_name = '" + table.getName() + "' and TABLE_SCHEMA='"+tableSchema+"'");
		pstmt = con.prepareStatement(strBuf.toString());
		rs = pstmt.executeQuery();

		while (rs.next())
		{
			Column column = new Column();
			column.setName(rs.getString(1));
			column.setComment(rs.getString(2));
			column.setType(rs.getString(3));
			table.addColumn(column);

		}
		printSelectSql(table,t1);
		printInsertSql(table,t1);
	}
	public static boolean isnull(String s){
		return  s==null||"".equals(s.trim());
	}
	public static String nullstr(String s){
		return isnull(s)?"":s;
	}

	public  void printSelectSql(Table table,String t1)
	{   
		String qz=isnull(t1)?"":t1+".";
		String sql ="SELECT * FROM "+table.getName().toUpperCase()+" "+nullstr(t1).toUpperCase();
		StringBuffer buf = new StringBuffer();
		List colList = table.getColumns();
		for (int i = 0; i < colList.size(); i++)
		{
			Column column = (Column) colList.get(i);
			String columnname = column.getName().toUpperCase();
			String datatype = column.getType().toUpperCase();
			if(datatype.equals("DATE")||datatype.equals("DATETIME")||datatype.equals("TIMESTAMP")){
				columnname="DATE_FORMAT("+qz.toUpperCase()+columnname+",'%Y-%m-%d') "+columnname;
			}else{
				columnname=qz.toUpperCase()+columnname;
			}
			
			buf.append(columnname).append(",");
		}
		if(colList.size()>0){
			buf.deleteCharAt(buf.length()-1);
		}
		sql=sql.replaceAll("\\*", buf.toString());
		System.out.println(sql);
	}
	public  void printInsertSql(Table table,String t1)
	{   
		String sql ="INSERT INTO "+table.getName().toUpperCase()+"(*) VALES() ";
		StringBuffer buf = new StringBuffer();
		List colList = table.getColumns();
		for (int i = 0; i < colList.size(); i++)
		{
			Column column = (Column) colList.get(i);
			String columnname = column.getName().toUpperCase();
			
			buf.append(columnname).append(",");
		}
		if(colList.size()>0){
			buf.deleteCharAt(buf.length()-1);
		}
		sql=sql.replaceAll("\\*", buf.toString());
		System.out.println(sql);
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
