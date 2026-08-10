package github.jiangbyte.io.common.core.enums;

/**
 * 数据权限范围类型：全部、仅本人、本部门、本部门及子级、自定义。
 *
 * Author: Charlie
 */
public enum DataScopeType {
    ALL,
    SELF,
    DEPT,
    DEPT_AND_CHILD,
    CUSTOM
}
