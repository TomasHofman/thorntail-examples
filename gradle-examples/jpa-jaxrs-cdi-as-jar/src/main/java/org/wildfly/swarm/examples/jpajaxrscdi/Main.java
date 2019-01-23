package org.wildfly.swarm.examples.jpajaxrscdi;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

/**
 * @author helio frota
 *
 */
public class Main {

    public static void main(String... args) throws Exception {

        Swarm swarm = new Swarm();

        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addPackage(Main.class.getPackage());
        deployment.addAsResource(EmptyAsset.INSTANCE, "META-INF/beans.xml");
        deployment.addAsResource("META-INF/load.sql");
        deployment.addAsResource("META-INF/persistence.xml");
        swarm.start().deploy(deployment);

    }
}
