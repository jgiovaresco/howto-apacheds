package com.github.howto.apacheds;

import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.server.annotations.CreateLdapServer;
import org.apache.directory.server.annotations.CreateTransport;
import org.apache.directory.server.core.annotations.ApplyLdifFiles;
import org.apache.directory.server.core.annotations.ContextEntry;
import org.apache.directory.server.core.annotations.CreateDS;
import org.apache.directory.server.core.annotations.CreateIndex;
import org.apache.directory.server.core.annotations.CreatePartition;
import org.apache.directory.server.core.integ.AbstractLdapTestUnit;
import org.apache.directory.server.core.integ.FrameworkRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(FrameworkRunner.class)
@CreateDS(name = "pe", partitions = {@CreatePartition(name = "ma-racine", suffix = "o=ma-racine", contextEntry = @ContextEntry(
        entryLdif = "dn: o=ma-racine\n" + "objectclass: top\n" + "objectclass: organization\n" + "o: ma-racine\n\n"),
        indexes = {@CreateIndex(attribute = "objectclass"), @CreateIndex(attribute = "o"), @CreateIndex(attribute = "ou")})})
@CreateLdapServer(transports = @CreateTransport(protocol = "LDAP", address = "localhost", port = 11389))
public class TestAvecAnnuaireLdap extends AbstractLdapTestUnit
{
   @Test
   @ApplyLdifFiles({"schema.ldif", "donnees.ldif"})
   public void testLdap() throws Exception
   {
      Assert.assertTrue(getService().getAdminSession().exists(new Dn("cn=99user,ou=schema")));
      Assert.assertTrue(
              getService().getAdminSession().exists(new Dn("m-oid=2.16.840.1.113730.3.1.55, ou=attributetypes, cn=99user, ou=schema")));
      Assert.assertTrue(
              getService().getAdminSession().exists(new Dn("m-oid=1.2.250.1.208.2.2.45, ou=objectclasses, cn=99user, ou=schema")));
      Assert.assertTrue(getService().getAdminSession().exists(new Dn("uid=SSY1,ou=interne,o=ma-racine")));
   }
}
