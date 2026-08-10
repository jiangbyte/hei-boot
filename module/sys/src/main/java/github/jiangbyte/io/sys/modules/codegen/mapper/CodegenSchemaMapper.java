package github.jiangbyte.io.sys.modules.codegen.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 代码生成数据库元数据查询 Mapper。
 *
 * Author: Charlie
 */
@Mapper
public interface CodegenSchemaMapper {

    @Select("""
            SELECT c.relname AS table_name,
                   COALESCE(obj_description(c.oid), '') AS table_comment
            FROM pg_class c
            JOIN pg_namespace n ON n.oid = c.relnamespace
            WHERE n.nspname = current_schema()
              AND c.relkind = 'r'
              AND c.relname NOT IN ('flyway_schema_history', 'sys_codegen_plan', 'sys_codegen_field')
            ORDER BY c.relname
            """)
    List<Map<String, Object>> listTables();

    @Select("""
            SELECT c.column_name,
                   COALESCE(pgd.description, '') AS column_comment,
                   c.data_type,
                   c.udt_name,
                   c.character_maximum_length AS max_length,
                   c.is_nullable,
                   c.ordinal_position AS sort
            FROM information_schema.columns c
            LEFT JOIN pg_catalog.pg_statio_all_tables st
              ON st.schemaname = c.table_schema AND st.relname = c.table_name
            LEFT JOIN pg_catalog.pg_description pgd
              ON pgd.objoid = st.relid AND pgd.objsubid = c.ordinal_position
            WHERE c.table_schema = current_schema()
              AND c.table_name = #{tableName}
            ORDER BY c.ordinal_position
            """)
    List<Map<String, Object>> listColumns(@Param("tableName") String tableName);

    @Select("""
            SELECT kcu.column_name
            FROM information_schema.table_constraints tc
            JOIN information_schema.key_column_usage kcu
              ON tc.constraint_name = kcu.constraint_name
             AND tc.table_schema = kcu.table_schema
            WHERE tc.table_schema = current_schema()
              AND tc.table_name = #{tableName}
              AND tc.constraint_type = 'PRIMARY KEY'
            """)
    List<String> listPrimaryKeys(@Param("tableName") String tableName);
}
