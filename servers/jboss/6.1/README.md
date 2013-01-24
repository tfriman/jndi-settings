## JBoss 6.1 configuration

Add following inside <server> tag to the file JBOSS_ROOT/server/<servername, usually default>/conf/jboss-service.xml

```xml
<mbean code="org.jboss.naming.JNDIBindingServiceMgr"
       name="jboss.tests:service=JNDIBindingServiceMgr">
  <attribute name="BindingsConfig" serialDataType="jbxb">
    <jndi:bindings
        xmlns:xs="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:jndi="urn:jboss:jndi-binding-service:1.0"
        xs:schemaLocation="urn:jboss:jndi-binding-service:1.0 resource:jndi-binding-service_1_0.xsd"
        >
      <jndi:binding name="baseUrl">
        <jndi:value type="java.net.URL" trim="true">
          http://www.iki.fi/ 
        </jndi:value>
      </jndi:binding>
      <jndi:binding name="dbVersion">
        <jndi:value type="java.lang.Long" trim="true">
          123
        </jndi:value>
      </jndi:binding>
      <jndi:binding name="customerCareAddress">
        <jndi:value type="java.lang.String" trim="true">
          xxx@xxx.com
        </jndi:value>
      </jndi:binding>
      <jndi:binding name="showAdmin">
        <jndi:value type="java.lang.Boolean" trim="true">
          true
        </jndi:value>
      </jndi:binding>
    </jndi:bindings>
  </attribute>
  <depends>jboss:service=Naming</depends>
</mbean>
```