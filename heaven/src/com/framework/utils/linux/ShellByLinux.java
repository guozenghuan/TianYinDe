package com.framework.utils.linux;

import java.io.IOException;

public class ShellByLinux {

	/**
	 * 执行linux命令
	 * @param cmd
	 */
	public static void shell(String cmd) {
		String[] cmds = { "/bin/sh", "-c", cmd };
		Process process;

		try {
			process = Runtime.getRuntime().exec(cmds);
			StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "Error");
			StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "Output");
			errorGobbler.start();
			outputGobbler.start();
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
