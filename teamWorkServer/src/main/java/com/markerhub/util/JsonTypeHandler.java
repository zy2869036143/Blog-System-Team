//package com.markerhub.util;
//
//import com.google.gson.Gson;
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
////
//public abstract class JsonTypeHandler<T>
//        extends BaseTypeHandler<T>
//        implements GenericTypeIdentified<T> {
//
//    protected Gson gson = new Gson();
//
//    protected Class<T> clazz = getGenericTypeClass();
//
//    @Override
//    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
//        ps.setString(i, this.toJson(parameter));
//    }
//
//    @Override
//    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
//        return this.toObject(rs.getString(columnName));
//    }
//
//    @Override
//    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//        return this.toObject(rs.getString(columnIndex));
//    }
//
//    @Override
//    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//        return this.toObject(cs.getString(columnIndex));
//    }
//
//    private String toJson(T object) {
//        try {
//            return gson.toJson(object);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private T toObject(String content) {
//        if (content != null && !content.isEmpty()) {
//            try {
//                return gson.fromJson(content, clazz);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//            return null;
//        }
//    }
//
//
//
//
//}
