package com.selegant.common.response;

import lombok.Data;

import java.util.List;

@Data
public class RoleInfo {


    /**
     * id : admin
     * name : 管理员
     * describe : 拥有所有权限
     * status : 1
     * creatorId : system
     * createTime : 1497160610259
     * deleted : 0
     * permissions : [{"roleId":"admin","permissionId":"dashboard","permissionName":"仪表盘","actions":"[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]","actionEntitySet":[{"action":"add","describe":"新增","defaultCheck":false},{"action":"query","describe":"查询","defaultCheck":false},{"action":"get","describe":"详情","defaultCheck":false},{"action":"update","describe":"修改","defaultCheck":false},{"action":"delete","describe":"删除","defaultCheck":false}],"actionList":null,"dataAccess":null},{"roleId":"admin","permissionId":"exception","permissionName":"异常页面权限","actions":"[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]","actionEntitySet":[{"action":"add","describe":"新增","defaultCheck":false},{"action":"query","describe":"查询","defaultCheck":false},{"action":"get","describe":"详情","defaultCheck":false},{"action":"update","describe":"修改","defaultCheck":false},{"action":"delete","describe":"删除","defaultCheck":false}],"actionList":null,"dataAccess":null},{"roleId":"admin","permissionId":"result","permissionName":"结果权限","actions":"[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]","actionEntitySet":[{"action":"add","describe":"新增","defaultCheck":false},{"action":"query","describe":"查询","defaultCheck":false},{"action":"get","describe":"详情","defaultCheck":false},{"action":"update","describe":"修改","defaultCheck":false},{"action":"delete","describe":"删除","defaultCheck":false}],"actionList":null,"dataAccess":null},{"roleId":"admin","permissionId":"profile","permissionName":"详细页权限","actions":"[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]","actionEntitySet":[{"action":"add","describe":"新增","defaultCheck":false},{"action":"query","describe":"查询","defaultCheck":false},{"action":"get","describe":"详情","defaultCheck":false},{"action":"update","describe":"修改","defaultCheck":false},{"action":"delete","describe":"删除","defaultCheck":false}],"actionList":null,"dataAccess":null},{"roleId":"admin","permissionId":"table","permissionName":"表格权限","actions":"[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"import\",\"defaultCheck\":false,\"describe\":\"导入\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"}]","actionEntitySet":[{"action":"add","describe":"新增","defaultCheck":false},{"action":"import","describe":"导入","defaultCheck":false},{"action":"get","describe":"详情","defaultCheck":false},{"action":"update","describe":"修改","defaultCheck":false}],"actionList":null,"dataAccess":null},{"roleId":"admin","permissionId":"form","permissionName":"表单权限","actions":"[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]","actionEntitySet":[{"action":"add","describe":"新增","defaultCheck":false},{"action":"get","describe":"详情","defaultCheck":false},{"action":"query","describe":"查询","defaultCheck":false},{"action":"update","describe":"修改","defaultCheck":false},{"action":"delete","describe":"删除","defaultCheck":false}],"actionList":null,"dataAccess":null},{"roleId":"admin","permissionId":"order","permissionName":"订单管理","actions":"[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]","actionEntitySet":[{"action":"add","describe":"新增","defaultCheck":false},{"action":"query","describe":"查询","defaultCheck":false},{"action":"get","describe":"详情","defaultCheck":false},{"action":"update","describe":"修改","defaultCheck":false},{"action":"delete","describe":"删除","defaultCheck":false}],"actionList":null,"dataAccess":null},{"roleId":"admin","permissionId":"permission","permissionName":"权限管理","actions":"[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]","actionEntitySet":[{"action":"add","describe":"新增","defaultCheck":false},{"action":"get","describe":"详情","defaultCheck":false},{"action":"update","describe":"修改","defaultCheck":false},{"action":"delete","describe":"删除","defaultCheck":false}],"actionList":null,"dataAccess":null},{"roleId":"admin","permissionId":"role","permissionName":"角色管理","actions":"[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]","actionEntitySet":[{"action":"add","describe":"新增","defaultCheck":false},{"action":"get","describe":"详情","defaultCheck":false},{"action":"update","describe":"修改","defaultCheck":false},{"action":"delete","describe":"删除","defaultCheck":false}],"actionList":null,"dataAccess":null},{"roleId":"admin","permissionId":"table","permissionName":"桌子管理","actions":"[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]","actionEntitySet":[{"action":"add","describe":"新增","defaultCheck":false},{"action":"get","describe":"详情","defaultCheck":false},{"action":"query","describe":"查询","defaultCheck":false},{"action":"update","describe":"修改","defaultCheck":false},{"action":"delete","describe":"删除","defaultCheck":false}],"actionList":null,"dataAccess":null},{"roleId":"admin","permissionId":"user","permissionName":"用户管理","actions":"[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"import\",\"defaultCheck\":false,\"describe\":\"导入\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"},{\"action\":\"export\",\"defaultCheck\":false,\"describe\":\"导出\"}]","actionEntitySet":[{"action":"add","describe":"新增","defaultCheck":false},{"action":"import","describe":"导入","defaultCheck":false},{"action":"get","describe":"详情","defaultCheck":false},{"action":"update","describe":"修改","defaultCheck":false},{"action":"delete","describe":"删除","defaultCheck":false},{"action":"export","describe":"导出","defaultCheck":false}],"actionList":null,"dataAccess":null}]
     */

    private String id;
    private String name;
    private String describe;
    private int status;
    private String creatorId;
    private long createTime;
    private int deleted;
    private List<PermissionsBean> permissions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public List<PermissionsBean> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionsBean> permissions) {
        this.permissions = permissions;
    }

    public static class PermissionsBean {
        /**
         * roleId : admin
         * permissionId : dashboard
         * permissionName : 仪表盘
         * actions : [{"action":"add","defaultCheck":false,"describe":"新增"},{"action":"query","defaultCheck":false,"describe":"查询"},{"action":"get","defaultCheck":false,"describe":"详情"},{"action":"update","defaultCheck":false,"describe":"修改"},{"action":"delete","defaultCheck":false,"describe":"删除"}]
         * actionEntitySet : [{"action":"add","describe":"新增","defaultCheck":false},{"action":"query","describe":"查询","defaultCheck":false},{"action":"get","describe":"详情","defaultCheck":false},{"action":"update","describe":"修改","defaultCheck":false},{"action":"delete","describe":"删除","defaultCheck":false}]
         * actionList : null
         * dataAccess : null
         */

        private String roleId;
        private String permissionId;
        private String permissionName;
        private String actions;
        private Object actionList;
        private Object dataAccess;
        private List<ActionEntitySetBean> actionEntitySet;

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getPermissionId() {
            return permissionId;
        }

        public void setPermissionId(String permissionId) {
            this.permissionId = permissionId;
        }

        public String getPermissionName() {
            return permissionName;
        }

        public void setPermissionName(String permissionName) {
            this.permissionName = permissionName;
        }

        public String getActions() {
            return actions;
        }

        public void setActions(String actions) {
            this.actions = actions;
        }

        public Object getActionList() {
            return actionList;
        }

        public void setActionList(Object actionList) {
            this.actionList = actionList;
        }

        public Object getDataAccess() {
            return dataAccess;
        }

        public void setDataAccess(Object dataAccess) {
            this.dataAccess = dataAccess;
        }

        public List<ActionEntitySetBean> getActionEntitySet() {
            return actionEntitySet;
        }

        public void setActionEntitySet(List<ActionEntitySetBean> actionEntitySet) {
            this.actionEntitySet = actionEntitySet;
        }

        public static class ActionEntitySetBean {
            /**
             * action : add
             * describe : 新增
             * defaultCheck : false
             */

            private String action;
            private String describe;
            private boolean defaultCheck;

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public boolean isDefaultCheck() {
                return defaultCheck;
            }

            public void setDefaultCheck(boolean defaultCheck) {
                this.defaultCheck = defaultCheck;
            }
        }
    }
}
