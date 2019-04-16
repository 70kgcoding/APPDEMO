package com.example.wdh.dao;

import android.content.Context;

import com.example.wdh.po.User;
import com.example.wdh.util.MyDatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

public class UserDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<User, Integer> dao;

    public UserDao(Context context) {
        this.context = context;
        try {
            this.dao = MyDatabaseHelper.getDatabaseHelper(context).getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 向user表中添加一条数据
    public void insert(User data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除user表中的一条数据
    public void delete(User data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByUsername(String username)
    {
        DeleteBuilder deleteBuilder=dao.deleteBuilder();
        try {
            deleteBuilder.where().eq("username",username);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteBySetDeleted(String username)
    {
        try {
            UpdateBuilder updateBuilder=dao.updateBuilder();
            updateBuilder.setWhere(updateBuilder.where().eq("username",username));
            updateBuilder.updateColumnValue("deleted",1).update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改user表中的一条数据
    public void update(User data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询user表中的所有未删除数据
    public List<User> selectAllNotDel() {
        List<User> users = null;
        try {
            users = dao.queryBuilder().where().ne("deleted",1).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> selectAll() {
        List<User> users = null;
        try {
            users = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // 根据ID取出用户信息
    public User queryById(int id) {
        User user = null;
        try {
            user = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // 根据用户名查询
    public User queryByName(String username)
    {
        User user=null;
        try {
            List<User> list= dao.queryBuilder().where().eq("username",username).query();
            if (!list.isEmpty())
                user=list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
