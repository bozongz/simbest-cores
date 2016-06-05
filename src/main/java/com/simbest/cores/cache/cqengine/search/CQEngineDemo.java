/**
 * 版权所有 © 北京晟壁科技有限公司 2008-2016。保留一切权利!
 */
package com.simbest.cores.cache.cqengine.search;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.index.hash.HashIndex;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.parser.sql.SQLParser;
import com.googlecode.cqengine.resultset.ResultSet;
import com.simbest.cores.admin.authority.model.SysUser;
import com.simbest.cores.cache.cqengine.index.SysUserIndex;

import static com.googlecode.cqengine.codegen.AttributeBytecodeGenerator.createAttributes;
import static com.googlecode.cqengine.query.QueryFactory.*;

/**
 * 用途： 
 * 作者: lishuyi 
 * 时间: 2016-06-03  18:16 
 */
public class CQEngineDemo {
    public static void main(String[] args) {
        IndexedCollection<SysUserIndex> sysUserOrgData = new ConcurrentIndexedCollection<SysUserIndex>();
        SQLParser<SysUserIndex> parser = SQLParser.forPojoWithAttributes(SysUserIndex.class, createAttributes(SysUserIndex.class));
        parser.registerAttribute(SysUserIndex.LOGIN_NAME);
        parser.registerAttribute(SysUserIndex.OWNER_ORG);
        parser.registerAttribute(SysUserIndex.POSITION);

        sysUserOrgData.addIndex(HashIndex.onAttribute(SysUserIndex.LOGIN_NAME));
        sysUserOrgData.addIndex(HashIndex.onAttribute(SysUserIndex.OWNER_ORG));
        sysUserOrgData.addIndex(HashIndex.onAttribute(SysUserIndex.POSITION));

        sysUserOrgData.add(new SysUserIndex(1,"chenhaiwei",1,"三级",null,null));
        sysUserOrgData.add(new SysUserIndex(2,"zhoupeng",2,"四级",null,null));
        sysUserOrgData.add(new SysUserIndex(3,"yangjixue",1,"四级",null,null));
        sysUserOrgData.add(new SysUserIndex(4,"zhaoxiang",3,"三级",null,null));

        Query<SysUserIndex> query;

        query = all(SysUserIndex.class);
        for (SysUserIndex uo : sysUserOrgData.retrieve(query, queryOptions(orderBy(ascending(SysUserIndex.LOGIN_NAME))))) {
            System.out.println(uo);
        }

        System.out.println("-----------------------------------------------------------------------------------------------");
        query = and(equal(SysUserIndex.LOGIN_NAME, "zhoupeng"), equal(SysUserIndex.POSITION, "四级"));
        for (SysUserIndex uo : sysUserOrgData.retrieve(query)) {
            System.out.println(uo);
        }

        System.out.println("-----------------------------------------------------------------------------------------------");
        ResultSet<SysUserIndex> results = parser.retrieve(sysUserOrgData, "SELECT * FROM sysUserData WHERE (ownerOrgId=1 AND position='四级')");
        for (SysUserIndex car : results) {
            System.out.println(car);
        }

        System.out.println("-----------------------------------------------------------------------------------------------");
        SysUserIndex oldVersion = sysUserOrgData.retrieve(equal(SysUserIndex.LOGIN_NAME, "yangjixue")).uniqueResult();
        sysUserOrgData.remove(oldVersion);
        query = all(SysUserIndex.class);
        for (SysUserIndex uo : sysUserOrgData.retrieve(query, queryOptions(orderBy(ascending(SysUserIndex.LOGIN_NAME))))) {
            System.out.println(uo);
        }
    }
}
