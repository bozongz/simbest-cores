package com.simbest.cores.admin.authority.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.simbest.cores.model.SystemModel;
import com.simbest.cores.utils.annotations.ExcelVOAttribute;
import com.simbest.cores.utils.annotations.NotNullColumn;
import com.simbest.cores.utils.annotations.ReferenceTable;
import com.simbest.cores.utils.annotations.ReferenceTables;
import com.simbest.cores.utils.annotations.Unique;

@Entity
@Table(name = "sys_org")
@ReferenceTables(joinTables={ @ReferenceTable(table="sys_org", value="上级组织"), 
		@ReferenceTable(table="sys_user", value="组织与用户")})
public class SysOrg extends SystemModel<SysOrg> {

	private static final long serialVersionUID = -1447427183198771078L;

    /**
     * 用于决策选择公司，分别表示相同组织、上级组织、所属公司、全部组织
     */
    public enum SwithOrgType{Same, Parent, Owner, all}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ExcelVOAttribute(name = "组织全路径", column = "D")
	@Column(name = "description", length = 200)
	private String description;
	
	@ExcelVOAttribute(name = "组织编码", column = "A")
	@NotNullColumn(value="组织编码")
	@Column(name = "orgCode", length = 20, unique = true)
	@Unique
	private String orgCode;
	
	@ExcelVOAttribute(name = "组织名称", column = "C")
	@NotNullColumn(value="组织名称")
	@Column(name = "orgName", nullable = false, length = 100)
	private String orgName;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="parent_id", nullable=true) //根节点允许为空
	private SysOrg parent; 

	@ExcelVOAttribute(name = "父组织编码", column = "B")
	@Transient
	private Integer parentId;
	
	@Transient
	private String parentName;
	
	@NotNullColumn(value="组织级别")
	@Column(nullable = true)
	private Integer orgLevel; 
	
	@NotNullColumn(value="组织类型")
	@Column(nullable = true)
	private Integer orgType; 
	
	@ExcelVOAttribute(name = "显示顺序", column = "E")
	@Column(nullable = true)
	private Integer orderBy; 
	
	@ExcelVOAttribute(name = "简称", column = "G")
	@Column(nullable = true)
	private String shortName; 
	
	@ExcelVOAttribute(name = "备注描述", column = "F")	
	@Column(nullable = true)
	private String remark; 
	
	public SysOrg() {
		super();
	}

	public SysOrg(Integer id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * @return the parent
	 */
	public SysOrg getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(SysOrg parent) {
		this.parent = parent;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * @return the orgLevel
	 */
	public Integer getOrgLevel() {
		return orgLevel;
	}

	/**
	 * @param orgLevel the orgLevel to set
	 */
	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	/**
	 * @return the orgType
	 */
	public Integer getOrgType() {
		return orgType;
	}

	/**
	 * @param orgType the orgType to set
	 */
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	/**
	 * @return the orderBy
	 */
	public Integer getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}