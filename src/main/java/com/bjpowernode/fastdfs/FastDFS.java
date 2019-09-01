package com.bjpowernode.fastdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.IOException;

/*
任涛 -----专用
*/
public class FastDFS {
    public static void main(String[] args) throws IOException, MyException {
        delete();
    }

    private static TrackerServer trackerServer = null;
    private static StorageServer storageServer = null;

    private static void upload(){
        StorageClient storageClient = null;
        try {
            storageClient = FastDFS.getStorageClient();

            String [] uploadArray = storageClient.upload_file("D:/321.txt","txt",null);
            for (String str:uploadArray) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            closeAll();
        }
    }
    private static void download(){
        try {
            StorageClient storageClient = FastDFS.getStorageClient();
            int num = storageClient.download_file("group1",
                    "M00/00/00/wKg6gF0evciAbx4NAAAACABxKxo758.txt","D:/aa.txt");
            System.out.println(num);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            closeAll();
        }

    }
    public static void delete(){
        try {
            StorageClient storageClient = FastDFS.getStorageClient();
            int i = storageClient.delete_file("group1", "M00/00/00/wKg6gF0er9OAXdBnAAAACVjiP1Y740.txt");
            System.out.println(i);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }finally {
            closeAll();
        }

    }
    public static StorageClient getStorageClient() throws IOException, MyException {
        //1.加载配置文件，默认去classpath下加载
        ClientGlobal.init("fdfs_client.conf");
        //2.创建TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //3.创建TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //4.创建StorageServler对象
        StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
        //5.创建StorageClient对象，这个对象完成对文件的操作
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        return storageClient;
    }
    public static void closeAll(){

        if(storageServer != null){
            try {
                storageServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(trackerServer != null){
            try {
                trackerServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
