package br.ufjf.egresso.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jonelo.jacksum.JacksumAPI;
import jonelo.jacksum.algorithm.AbstractChecksum;

/**Classe para lidar com o salvamento e a exclusão de arquivos
 * 
 * @author Matheus Marques
 *
 */
public class FileManager {
	
	public static String saveFileInputSream(InputStream fileIS, String fileExt) {
		String fileName = null;
		OutputStream outputStream = null;
		if (fileExt != null && fileExt.trim() != "") {
			try {
				AbstractChecksum checksum = JacksumAPI
						.getChecksumInstance("md5");
				checksum.update(("" + System.currentTimeMillis()).getBytes());
				fileName = checksum.getFormattedValue() + "." + fileExt;

				outputStream = new FileOutputStream(new File(
						ConfHandler.getConf("FILE.PATH") + fileName));

				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = fileIS.read(bytes)) != -1)
					outputStream.write(bytes, 0, read);

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				try {
					if (fileIS != null)
						fileIS.close();
					if (outputStream != null)
						outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return fileName;
	}

}
