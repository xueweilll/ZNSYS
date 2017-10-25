package com.jfsl.util;

import java.io.File;

public class FileOperator {
	
	//�½��ļ���
	public static void mkFolder(String folderPath)
	{
		File savedir = new File(folderPath);
		if (!savedir.exists()) savedir.mkdirs();
	}
	
	//�ļ���������
	public static void renameFolder(String folderPath1,String folderPath2)
	{
		File savedir = new File(folderPath1);
		File desdir = new File(folderPath2);
		if (savedir.exists()&&!desdir.exists()) savedir.renameTo(desdir);
	}
	
	//ɾ���ļ���
	//param folderPath �ļ�����������·��
	public static void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //ɾ����������������
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        File myFilePath = new File(filePath);
	        myFilePath.delete(); //ɾ�����ļ���
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}

	//ɾ��ָ���ļ����������ļ�
	//param path �ļ�����������·��
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);//��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);//��ɾ�����ļ���
				flag = true;
			}
		}
		return flag;
	}
}
