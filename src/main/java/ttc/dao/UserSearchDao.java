package ttc.dao;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ttc.bean.UserBean;
import ttc.bean.Bean;

import ttc.exception.integration.IntegrationException;

import ttc.util.MySqlConnectionManager;

public class UserSearchDao implements AbstractDao{
    public int insert(Map map)throws IntegrationException{
        return 0;
    }
    public int update(Map map)throws IntegrationException{
        return 0;
    }
    public Bean read(Map map)throws IntegrationException{
        return null;
    }

    public List readAll(Map map)throws IntegrationException{
        List result = new ArrayList();
        PreparedStatement pst = null;
        String keyword = (String)map.get("keyword");


        try{
            Connection cn = null;
            cn = MySqlConnectionManager.getInstance().getConnection();
            StringBuffer sql = new StringBuffer();
            sql.append("select user_id,user_name,sex,user_icon_path,mail_address,");
            sql.append("user_profile,admin_flag ");
            sql.append("from users where (user_name like ? or");
            sql.append("  login_id like ? or");
            sql.append("  user_name_kana like ? or");
            sql.append("  birth_date like ? or");
            sql.append("  mail_address like ? )");

            sql.append(" and user_status_flag=0");


			pst = cn.prepareStatement(new String(sql));

System.out.println(sql);
            pst.setString(1,"%"+keyword+"%");
            pst.setString(2,"%"+keyword+"%");
            pst.setString(3,"%"+keyword+"%");
            pst.setString(4,"%"+keyword+"%");
			pst.setString(5,"%"+keyword+"%");

            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                UserBean user = new UserBean();
                user.setId(rs.getString(1));
                user.setUserName(rs.getString(2));
                user.setSex(rs.getString(3));
                user.setIconPath(rs.getString(4));
                user.setMailAddress(rs.getString(5));
                user.setProfile(rs.getString(6));
                user.setAdminFlag(rs.getString(7));

                result.add(user);
            }

        }catch(SQLException e){
            throw new IntegrationException(e.getMessage(),e);
        }finally{
            try{
                if(pst!=null){
                    pst.close();
                }
            }catch(SQLException e){
                throw new IntegrationException(e.getMessage(),e);
            }
        }

        return result;
    }
}
