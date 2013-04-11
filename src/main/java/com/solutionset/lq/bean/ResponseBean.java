package com.solutionset.lq.bean;

public class ResponseBean {
	
	private String database_id;
    private String appl_id;
    private String username;
    private String password;
    private String user_token;
    private String external_tran_id;
    private String member_id;
    private String lang_code;
    private String ytd_nights;
    private String ytd_base_points;
    private String ytd_bonus_points;
    private String ytd_points_redeemed;
    private String ytd_other_points;
    
    public String toString(){
    	
    	String retVal = new String();
    		
    	retVal += "database_id: " + database_id + "\n";
        retVal += "appl_id: " + appl_id + "\n";
        retVal += "username: " + username + "\n";
        retVal += "password: " + password + "\n";
        retVal += "user_token: " + user_token + "\n";
        retVal += "external_tran_id: " + external_tran_id + "\n";
        retVal += "member_id: " + member_id + "\n";
        retVal += "lang_code: " + lang_code + "\n";
        retVal += "ytd_nights: " + ytd_nights + "\n";
        retVal += "ytd_base_points: " + ytd_base_points + "\n";
        retVal += "ytd_bonus_points: " + ytd_bonus_points + "\n";
        retVal += "ytd_points_redeemed: " + ytd_points_redeemed + "\n";
        retVal += "ytd_points_redeemed: " + ytd_other_points;    
        
        return retVal;

    }

    public void loadValue(String name, String value) {
    	
    	if (name == "database_id") {
    		
    		this.database_id = value;
    		
    	} else if (name == "appl_id") {
    		
    		this.appl_id = value;
    		
    	} else if (name == "username") {
    		
    		this.username = value;
    		
    	} else if (name == "password") {
    		
    		this.password = value;
    		
    	} else if (name == "user_token") {
    		
    		this.user_token = value;
    		
    	} else if (name == "external_tran_id") {
    		
    		this.external_tran_id = value;
    		
    	} else if (name == "member_id") {
    		
    		this.member_id = value;
    		
    	} else if (name == "ytd_nights") {
    		
    		this.ytd_nights = value;
    		
    	} else if (name == "ytd_base_points") {
    		
    		this.ytd_base_points = value;
    		
    	} else if (name == "ytd_bonus_points") {
    		
    		this.ytd_bonus_points = value;
    		
    	} else if (name == "ytd_points_redeemed") {
    		
    		this.ytd_points_redeemed = value;
    		
    	} else if (name == "ytd_other_points") {
    		
    		this.ytd_other_points = value;
    		
    	};
    	
    }
    
    public String getDatabase_id() {
		return database_id;
	}
	public void setDatabase_id(String database_id) {
		this.database_id = database_id;
	}
	public String getAppl_id() {
		return appl_id;
	}
	public void setAppl_id(String appl_id) {
		this.appl_id = appl_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_token() {
		return user_token;
	}
	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}
	public String getExternal_tran_id() {
		return external_tran_id;
	}
	public void setExternal_tran_id(String external_tran_id) {
		this.external_tran_id = external_tran_id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getLang_code() {
		return lang_code;
	}
	public void setLang_code(String lang_code) {
		this.lang_code = lang_code;
	}
	public String getYtd_nights() {
		return ytd_nights;
	}
	public void setYtd_nights(String ytd_nights) {
		this.ytd_nights = ytd_nights;
	}
	public String getYtd_base_points() {
		return ytd_base_points;
	}
	public void setYtd_base_points(String ytd_base_points) {
		this.ytd_base_points = ytd_base_points;
	}
	public String getYtd_bonus_points() {
		return ytd_bonus_points;
	}
	public void setYtd_bonus_points(String ytd_bonus_points) {
		this.ytd_bonus_points = ytd_bonus_points;
	}
	public String getYtd_points_redeemed() {
		return ytd_points_redeemed;
	}
	public void setYtd_points_redeemed(String ytd_points_redeemed) {
		this.ytd_points_redeemed = ytd_points_redeemed;
	}
	public String getYtd_other_points() {
		return ytd_other_points;
	}
	public void setYtd_other_points(String ytd_other_points) {
		this.ytd_other_points = ytd_other_points;
	}
	
	
	
}
