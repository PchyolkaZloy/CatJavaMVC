package en.pchz.common;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;


public class CatColorType implements UserType<CatColor> {
    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

    @Override
    public Class<CatColor> returnedClass() {
        return CatColor.class;
    }

    @Override
    public boolean equals(CatColor catColor, CatColor j1) {
        return Objects.equals(catColor, j1);
    }

    @Override
    public int hashCode(CatColor catColor) {
        return catColor.hashCode();
    }

    @Override
    public CatColor nullSafeGet(ResultSet resultSet, int i, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws SQLException {
        String value = resultSet.getString(i);
        return resultSet.wasNull() ? null : CatColor.valueOf(value.toUpperCase());
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, CatColor catColor, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws SQLException {
        if (catColor == null) {
            preparedStatement.setNull(i, Types.VARCHAR);
        } else {
            preparedStatement.setString(i, catColor.name().toLowerCase());
        }
    }

    @Override
    public CatColor deepCopy(CatColor catColor) {
        return catColor;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(CatColor catColor) {
        return catColor;
    }

    @Override
    public CatColor assemble(Serializable serializable, Object o) {
        return (CatColor) serializable;
    }
}