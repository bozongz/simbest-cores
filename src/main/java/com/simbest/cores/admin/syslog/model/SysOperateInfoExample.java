package com.simbest.cores.admin.syslog.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysOperateInfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    public SysOperateInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andClientIpIsNull() {
            addCriterion("client_ip is null");
            return (Criteria) this;
        }

        public Criteria andClientIpIsNotNull() {
            addCriterion("client_ip is not null");
            return (Criteria) this;
        }

        public Criteria andClientIpEqualTo(String value) {
            addCriterion("client_ip =", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotEqualTo(String value) {
            addCriterion("client_ip <>", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpGreaterThan(String value) {
            addCriterion("client_ip >", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpGreaterThanOrEqualTo(String value) {
            addCriterion("client_ip >=", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpLessThan(String value) {
            addCriterion("client_ip <", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpLessThanOrEqualTo(String value) {
            addCriterion("client_ip <=", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpLike(String value) {
            addCriterion("client_ip like", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotLike(String value) {
            addCriterion("client_ip not like", value, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpIn(List<String> values) {
            addCriterion("client_ip in", values, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotIn(List<String> values) {
            addCriterion("client_ip not in", values, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpBetween(String value1, String value2) {
            addCriterion("client_ip between", value1, value2, "clientIp");
            return (Criteria) this;
        }

        public Criteria andClientIpNotBetween(String value1, String value2) {
            addCriterion("client_ip not between", value1, value2, "clientIp");
            return (Criteria) this;
        }

        public Criteria andFunCodeIsNull() {
            addCriterion("fun_code is null");
            return (Criteria) this;
        }

        public Criteria andFunCodeIsNotNull() {
            addCriterion("fun_code is not null");
            return (Criteria) this;
        }

        public Criteria andFunCodeEqualTo(String value) {
            addCriterion("fun_code =", value, "funCode");
            return (Criteria) this;
        }

        public Criteria andFunCodeNotEqualTo(String value) {
            addCriterion("fun_code <>", value, "funCode");
            return (Criteria) this;
        }

        public Criteria andFunCodeGreaterThan(String value) {
            addCriterion("fun_code >", value, "funCode");
            return (Criteria) this;
        }

        public Criteria andFunCodeGreaterThanOrEqualTo(String value) {
            addCriterion("fun_code >=", value, "funCode");
            return (Criteria) this;
        }

        public Criteria andFunCodeLessThan(String value) {
            addCriterion("fun_code <", value, "funCode");
            return (Criteria) this;
        }

        public Criteria andFunCodeLessThanOrEqualTo(String value) {
            addCriterion("fun_code <=", value, "funCode");
            return (Criteria) this;
        }

        public Criteria andFunCodeLike(String value) {
            addCriterion("fun_code like", value, "funCode");
            return (Criteria) this;
        }

        public Criteria andFunCodeNotLike(String value) {
            addCriterion("fun_code not like", value, "funCode");
            return (Criteria) this;
        }

        public Criteria andFunCodeIn(List<String> values) {
            addCriterion("fun_code in", values, "funCode");
            return (Criteria) this;
        }

        public Criteria andFunCodeNotIn(List<String> values) {
            addCriterion("fun_code not in", values, "funCode");
            return (Criteria) this;
        }

        public Criteria andFunCodeBetween(String value1, String value2) {
            addCriterion("fun_code between", value1, value2, "funCode");
            return (Criteria) this;
        }

        public Criteria andFunCodeNotBetween(String value1, String value2) {
            addCriterion("fun_code not between", value1, value2, "funCode");
            return (Criteria) this;
        }

        public Criteria andFunNameIsNull() {
            addCriterion("fun_name is null");
            return (Criteria) this;
        }

        public Criteria andFunNameIsNotNull() {
            addCriterion("fun_name is not null");
            return (Criteria) this;
        }

        public Criteria andFunNameEqualTo(String value) {
            addCriterion("fun_name =", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameNotEqualTo(String value) {
            addCriterion("fun_name <>", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameGreaterThan(String value) {
            addCriterion("fun_name >", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameGreaterThanOrEqualTo(String value) {
            addCriterion("fun_name >=", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameLessThan(String value) {
            addCriterion("fun_name <", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameLessThanOrEqualTo(String value) {
            addCriterion("fun_name <=", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameLike(String value) {
            addCriterion("fun_name like", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameNotLike(String value) {
            addCriterion("fun_name not like", value, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameIn(List<String> values) {
            addCriterion("fun_name in", values, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameNotIn(List<String> values) {
            addCriterion("fun_name not in", values, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameBetween(String value1, String value2) {
            addCriterion("fun_name between", value1, value2, "funName");
            return (Criteria) this;
        }

        public Criteria andFunNameNotBetween(String value1, String value2) {
            addCriterion("fun_name not between", value1, value2, "funName");
            return (Criteria) this;
        }

        public Criteria andLoginnameIsNull() {
            addCriterion("loginname is null");
            return (Criteria) this;
        }

        public Criteria andLoginnameIsNotNull() {
            addCriterion("loginname is not null");
            return (Criteria) this;
        }

        public Criteria andLoginnameEqualTo(String value) {
            addCriterion("loginname =", value, "loginname");
            return (Criteria) this;
        }

        public Criteria andLoginnameNotEqualTo(String value) {
            addCriterion("loginname <>", value, "loginname");
            return (Criteria) this;
        }

        public Criteria andLoginnameGreaterThan(String value) {
            addCriterion("loginname >", value, "loginname");
            return (Criteria) this;
        }

        public Criteria andLoginnameGreaterThanOrEqualTo(String value) {
            addCriterion("loginname >=", value, "loginname");
            return (Criteria) this;
        }

        public Criteria andLoginnameLessThan(String value) {
            addCriterion("loginname <", value, "loginname");
            return (Criteria) this;
        }

        public Criteria andLoginnameLessThanOrEqualTo(String value) {
            addCriterion("loginname <=", value, "loginname");
            return (Criteria) this;
        }

        public Criteria andLoginnameLike(String value) {
            addCriterion("loginname like", value, "loginname");
            return (Criteria) this;
        }

        public Criteria andLoginnameNotLike(String value) {
            addCriterion("loginname not like", value, "loginname");
            return (Criteria) this;
        }

        public Criteria andLoginnameIn(List<String> values) {
            addCriterion("loginname in", values, "loginname");
            return (Criteria) this;
        }

        public Criteria andLoginnameNotIn(List<String> values) {
            addCriterion("loginname not in", values, "loginname");
            return (Criteria) this;
        }

        public Criteria andLoginnameBetween(String value1, String value2) {
            addCriterion("loginname between", value1, value2, "loginname");
            return (Criteria) this;
        }

        public Criteria andLoginnameNotBetween(String value1, String value2) {
            addCriterion("loginname not between", value1, value2, "loginname");
            return (Criteria) this;
        }

        public Criteria andMoudleCodeIsNull() {
            addCriterion("moudle_code is null");
            return (Criteria) this;
        }

        public Criteria andMoudleCodeIsNotNull() {
            addCriterion("moudle_code is not null");
            return (Criteria) this;
        }

        public Criteria andMoudleCodeEqualTo(String value) {
            addCriterion("moudle_code =", value, "moudleCode");
            return (Criteria) this;
        }

        public Criteria andMoudleCodeNotEqualTo(String value) {
            addCriterion("moudle_code <>", value, "moudleCode");
            return (Criteria) this;
        }

        public Criteria andMoudleCodeGreaterThan(String value) {
            addCriterion("moudle_code >", value, "moudleCode");
            return (Criteria) this;
        }

        public Criteria andMoudleCodeGreaterThanOrEqualTo(String value) {
            addCriterion("moudle_code >=", value, "moudleCode");
            return (Criteria) this;
        }

        public Criteria andMoudleCodeLessThan(String value) {
            addCriterion("moudle_code <", value, "moudleCode");
            return (Criteria) this;
        }

        public Criteria andMoudleCodeLessThanOrEqualTo(String value) {
            addCriterion("moudle_code <=", value, "moudleCode");
            return (Criteria) this;
        }

        public Criteria andMoudleCodeLike(String value) {
            addCriterion("moudle_code like", value, "moudleCode");
            return (Criteria) this;
        }

        public Criteria andMoudleCodeNotLike(String value) {
            addCriterion("moudle_code not like", value, "moudleCode");
            return (Criteria) this;
        }

        public Criteria andMoudleCodeIn(List<String> values) {
            addCriterion("moudle_code in", values, "moudleCode");
            return (Criteria) this;
        }

        public Criteria andMoudleCodeNotIn(List<String> values) {
            addCriterion("moudle_code not in", values, "moudleCode");
            return (Criteria) this;
        }

        public Criteria andMoudleCodeBetween(String value1, String value2) {
            addCriterion("moudle_code between", value1, value2, "moudleCode");
            return (Criteria) this;
        }

        public Criteria andMoudleCodeNotBetween(String value1, String value2) {
            addCriterion("moudle_code not between", value1, value2, "moudleCode");
            return (Criteria) this;
        }

        public Criteria andMoudleNameIsNull() {
            addCriterion("moudle_name is null");
            return (Criteria) this;
        }

        public Criteria andMoudleNameIsNotNull() {
            addCriterion("moudle_name is not null");
            return (Criteria) this;
        }

        public Criteria andMoudleNameEqualTo(String value) {
            addCriterion("moudle_name =", value, "moudleName");
            return (Criteria) this;
        }

        public Criteria andMoudleNameNotEqualTo(String value) {
            addCriterion("moudle_name <>", value, "moudleName");
            return (Criteria) this;
        }

        public Criteria andMoudleNameGreaterThan(String value) {
            addCriterion("moudle_name >", value, "moudleName");
            return (Criteria) this;
        }

        public Criteria andMoudleNameGreaterThanOrEqualTo(String value) {
            addCriterion("moudle_name >=", value, "moudleName");
            return (Criteria) this;
        }

        public Criteria andMoudleNameLessThan(String value) {
            addCriterion("moudle_name <", value, "moudleName");
            return (Criteria) this;
        }

        public Criteria andMoudleNameLessThanOrEqualTo(String value) {
            addCriterion("moudle_name <=", value, "moudleName");
            return (Criteria) this;
        }

        public Criteria andMoudleNameLike(String value) {
            addCriterion("moudle_name like", value, "moudleName");
            return (Criteria) this;
        }

        public Criteria andMoudleNameNotLike(String value) {
            addCriterion("moudle_name not like", value, "moudleName");
            return (Criteria) this;
        }

        public Criteria andMoudleNameIn(List<String> values) {
            addCriterion("moudle_name in", values, "moudleName");
            return (Criteria) this;
        }

        public Criteria andMoudleNameNotIn(List<String> values) {
            addCriterion("moudle_name not in", values, "moudleName");
            return (Criteria) this;
        }

        public Criteria andMoudleNameBetween(String value1, String value2) {
            addCriterion("moudle_name between", value1, value2, "moudleName");
            return (Criteria) this;
        }

        public Criteria andMoudleNameNotBetween(String value1, String value2) {
            addCriterion("moudle_name not between", value1, value2, "moudleName");
            return (Criteria) this;
        }

        public Criteria andOperatetimeIsNull() {
            addCriterion("operatetime is null");
            return (Criteria) this;
        }

        public Criteria andOperatetimeIsNotNull() {
            addCriterion("operatetime is not null");
            return (Criteria) this;
        }

        public Criteria andOperatetimeEqualTo(Date value) {
            addCriterion("operatetime =", value, "operatetime");
            return (Criteria) this;
        }

        public Criteria andOperatetimeNotEqualTo(Date value) {
            addCriterion("operatetime <>", value, "operatetime");
            return (Criteria) this;
        }

        public Criteria andOperatetimeGreaterThan(Date value) {
            addCriterion("operatetime >", value, "operatetime");
            return (Criteria) this;
        }

        public Criteria andOperatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("operatetime >=", value, "operatetime");
            return (Criteria) this;
        }

        public Criteria andOperatetimeLessThan(Date value) {
            addCriterion("operatetime <", value, "operatetime");
            return (Criteria) this;
        }

        public Criteria andOperatetimeLessThanOrEqualTo(Date value) {
            addCriterion("operatetime <=", value, "operatetime");
            return (Criteria) this;
        }

        public Criteria andOperatetimeIn(List<Date> values) {
            addCriterion("operatetime in", values, "operatetime");
            return (Criteria) this;
        }

        public Criteria andOperatetimeNotIn(List<Date> values) {
            addCriterion("operatetime not in", values, "operatetime");
            return (Criteria) this;
        }

        public Criteria andOperatetimeBetween(Date value1, Date value2) {
            addCriterion("operatetime between", value1, value2, "operatetime");
            return (Criteria) this;
        }

        public Criteria andOperatetimeNotBetween(Date value1, Date value2) {
            addCriterion("operatetime not between", value1, value2, "operatetime");
            return (Criteria) this;
        }

        public Criteria andSessionidIsNull() {
            addCriterion("sessionid is null");
            return (Criteria) this;
        }

        public Criteria andSessionidIsNotNull() {
            addCriterion("sessionid is not null");
            return (Criteria) this;
        }

        public Criteria andSessionidEqualTo(String value) {
            addCriterion("sessionid =", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidNotEqualTo(String value) {
            addCriterion("sessionid <>", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidGreaterThan(String value) {
            addCriterion("sessionid >", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidGreaterThanOrEqualTo(String value) {
            addCriterion("sessionid >=", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidLessThan(String value) {
            addCriterion("sessionid <", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidLessThanOrEqualTo(String value) {
            addCriterion("sessionid <=", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidLike(String value) {
            addCriterion("sessionid like", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidNotLike(String value) {
            addCriterion("sessionid not like", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidIn(List<String> values) {
            addCriterion("sessionid in", values, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidNotIn(List<String> values) {
            addCriterion("sessionid not in", values, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidBetween(String value1, String value2) {
            addCriterion("sessionid between", value1, value2, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidNotBetween(String value1, String value2) {
            addCriterion("sessionid not between", value1, value2, "sessionid");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sys_operate_info
     *
     * @mbggenerated do_not_delete_during_merge Sun Aug 17 21:36:32 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sys_operate_info
     *
     * @mbggenerated Sun Aug 17 21:36:32 CST 2014
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}