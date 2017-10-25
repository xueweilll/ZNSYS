package sychel.com.util;

import java.io.File;
import java.io.IOException;
 
/**
 * Oracle���ݿⱸ��
 * 
 * @author GaoHuanjie
 */
public class OracleDatabaseBackup {
 
    /**
     * Java����ʵ��Oracle���ݿ⵼��
     * 
     * @author GaoHuanjie
     * @param userName �������ݿ�����Ҫ���û���
     * @param password �������ݿ�����Ҫ������
     * @param SID �û����ڵ�SID
     * @param savePath ���ݿ⵼���ļ�����·��
     * @param fileName ���ݿ⵼���ļ��ļ���
     * @return ����true��ʾ�����ɹ������򷵻�false��
     */
    public static boolean exportDatabaseTool(String userName, String password, String SID, String savePath, String fileName) throws InterruptedException {
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// ���Ŀ¼������
            saveFile.mkdirs();// �����ļ���
        }
        try {
            Process process = Runtime.getRuntime().exec("exp " + userName + "/" + password + "@" + SID + " file=" + savePath + "/" + fileName + ".dmp");
            System.out.println("exp " + userName + "/" + password + "@" + SID + " file=" + savePath + "/" + fileName + ".dmp");
            if(process.waitFor() == 0){//0 ��ʾ�߳�������ֹ�� 
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
 
    public static void main(String[] args) throws InterruptedException {
        if (exportDatabaseTool("scott", "tgq2012", "syy_data", "c:/BackupDatabase", "oracledb")) {
            System.out.println("���ݿ�ɹ����ݣ�����");
        } else {
            System.out.println("���ݿⱸ��ʧ�ܣ�����");
        }
    }
}

