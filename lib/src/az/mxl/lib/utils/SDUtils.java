package az.mxl.lib.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import az.mxl.lib.base.AppController;
import az.mxl.lib.log.LogUtils;

public class SDUtils {

	static LogUtils log = LogUtils.getLog(SDUtils.class);

	private static final int MB = 1024 * 1024;
	private static final int CACHE_SIZE = 30;// 缓存大小
	private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 20;// sd卡的剩余最小空间

	/**
	 * 检查sd卡的状态
	 * 
	 * @return ture:SD卡可用，false:SD卡不可用。
	 */
	private static boolean checkSDCard() {
		log.w("sd卡状态：" + Environment.getExternalStorageState());
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取sd卡的路径
	 * 
	 * @return
	 */
	private static String getSDDirectory() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	/**
	 * 是否有缓存空间（sd卡可用或者手机自带内存大于8M）
	 * 
	 * @return
	 */
	public static boolean isHaveCacheMemory() {
		if (checkSDCard()) {
			return true;
		} else if (getAvailableInternalMemorySize() >= 8) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取缓存目录 --> sd卡优先，如果sd卡不可用则返回手机自带存储目录(/data/data/包名/cache/?)
	 * 
	 * @param context
	 * @return
	 */
	public static String getCacheDirectory() {
		if (checkSDCard()) {
			return getSDDirectory();
		} else {
			return AppController.getInstance().getApplicationContext().getCacheDir().getAbsolutePath();
		}
	}

	/**
	 * 获取手机内部存储
	 * 
	 * @return 单位为M
	 */
	private static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory(); // 获取数据目录
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return (availableBlocks * blockSize) / (1024 * 1024);
	}

	/**
	 * 查看文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean checkFileExists(File file) {
		return file.exists();
	}

	/**
	 * 获取文件夹大小
	 * 
	 * @param file
	 *            File实例
	 * @return long 单位为kb
	 */
	public static long getFolderSize(File file) {
		long size = 0;
		if (file.isDirectory()) {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isDirectory()) {
					size = size + getFolderSize(fileList[i]);
				} else {
					size = size + fileList[i].length();
				}
			}
		} else {
			size = size + file.length();
		}
		Log.e("AZ", "this mp3 size is " + size / 1024 + "kb");
		return size / 1024;
	}

	/**
	 * 删除缓存文件(1、缓存文件大于设定的缓存最大值；2、sd卡剩余空间小于设定的剩余空间最小值)
	 * 
	 * @param dirPath
	 * @return
	 */
	public static boolean removeCache(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return true;
		}
		if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			return false;
		}

		int dirSize = 0;
		for (int i = 0; i < files.length; i++) {
			// if (files[i].getName().endsWith(WHOLESALE_CONV)) {
			dirSize += files[i].length();
			// }
		}

		if (dirSize > CACHE_SIZE * MB || FREE_SD_SPACE_NEEDED_TO_CACHE > getFreeSpaceOnSd()) {
			int removeFactor = (int) ((0.4 * files.length) + 1);
			Arrays.sort(files, new FileLastModifSort());// files按照最后修改时间排序
			for (int i = 0; i < removeFactor; i++) {
				// if (files[i].getName().contains(WHOLESALE_CONV)) {//
				// 确认该files有“.cach”字段
				files[i].delete();
				// }
			}
		}

		if (getFreeSpaceOnSd() <= CACHE_SIZE) {
			return false;
		}
		return true;
	}

	/**
	 * 得到sd卡上的空余空间(不是自己“缓存文件”的剩余空间)
	 * 
	 * @return
	 */
	private static int getFreeSpaceOnSd() {
		StatFs stat = new StatFs(getSDDirectory());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;
		return (int) sdFreeMB;
	}

	/**
	 * 要对集合对象或数组对象进行排序，需要实现Comparator接口以达到我们想要的目标。
	 * 
	 * <pre>
	 * 如果file1小于file2,返回一个负数;
	 * </pre>
	 * 
	 * <pre>
	 * 如果file1大于file2,返回一个正数;
	 * </pre>
	 * 
	 * <pre>
	 * 如果他们相等，则返回0。
	 * </pre>
	 */
	private static class FileLastModifSort implements Comparator<File> {
		public int compare(File file1, File file2) {
			if (file1.lastModified() > file2.lastModified()) {
				return 1;
			} else if (file1.lastModified() == file2.lastModified()) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	/**
	 * 将SD卡文件删除
	 * 
	 * @param file
	 */
	public static void deleteFile(final File file) {
		if (checkSDCard()) {
			new Thread() {
				@Override
				public void run() {
					if (file.exists()) {
						if (file.isFile()) { // 如果它是一个文件
							file.delete();
						} else if (file.isDirectory()) {// 如果它是一个目录
							// 声明目录下所有的文件 files[];
							File files[] = file.listFiles();
							for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
								deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
							}
							// file.delete();// 删除目录
						}
					}
					log.w("删除mp3文件");
				}
			}.start();
		}
	}

}
