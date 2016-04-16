/**
 * 
 */
package org.activejpa.enhancer;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.security.CodeSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

/**
 * @author ganeshs
 *
 */
public class ActiveJpaAgentLoader {

	private static final Logger logger = LoggerFactory.getLogger(ActiveJpaAgentLoader.class);

	private static final ActiveJpaAgentLoader loader = new ActiveJpaAgentLoader();

	private ActiveJpaAgentLoader() {
	}

	public static ActiveJpaAgentLoader instance() {
		return loader;
	}

	public void loadAgent() throws RuntimeException {
		logger.info("dynamically loading javaagent");
		String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
		int p = nameOfRunningVM.indexOf('@');
		String pid = nameOfRunningVM.substring(0, p);

		try {
			VirtualMachine vm = VirtualMachine.attach(pid);
			CodeSource codeSource = ActiveJpaAgent.class.getProtectionDomain().getCodeSource();
			String OS = System.getProperty("os.name");
			String path = codeSource.getLocation().toURI().getPath();
			if (path != null && path.startsWith("/") && OS.startsWith("Windows")) {
				path = path.substring(1);
			}
			vm.loadAgent(path, "");
			vm.detach();
		} catch (AttachNotSupportedException | IOException | URISyntaxException | AgentLoadException
				| AgentInitializationException e) {
			throw new RuntimeException(e);
		}
	}
}
