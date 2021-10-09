package com.hd.plugin_gradle

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

//import java.util.logging.Logger

class HdChangeTransform extends Transform {
    private Project mProject;
//    private Logger logger

    HdChangeTransform(Project _project) {
        this.mProject = _project;
        System.out.println("---------------- HdChangeTransform --------------")
//        this.logger = project.logger
    }

    @Override
    public String getName() {
        return "HdChangeTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }


    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(Context context,
                          Collection<TransformInput> inputs,
                          Collection<TransformInput> referencedInputs,
                          TransformOutputProvider outputProvider,
                          boolean isIncremental) throws IOException, TransformException, InterruptedException {
        System.out.println("---------------- 进入transform了 --------------")

        //遍历input
        System.out.println("---------------- 遍历input --------------")
        inputs.each { TransformInput input ->
            //遍历文件夹
            System.out.println("---------------- 遍历文件夹 --------------")
            input.directoryInputs.each { DirectoryInput directoryInput ->
                //注入代码
                System.out.println("---------------- 注入代码 --------------")
                MyInject.inject(directoryInput.file.absolutePath, mProject)

                System.out.println("---------------- 获取output目录 --------------")
                // 获取output目录
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                //这里写代码片

                System.out.println("---------------- 将input的目录复制到output指定目录 --------------")
                // 将input的目录复制到output指定目录
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            System.out.println("---------------- 遍历jar文件 对jar不操作，但是要输出到out路径 --------------")
            // 遍历jar文件 对jar不操作，但是要输出到out路径
            input.jarInputs.each { JarInput jarInput ->
//                 重命名输出文件（同目录copyFile会冲突）
//                def jarName = jarInput.name
//                println("jar = " + jarInput.file.getAbsolutePath())
//                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
//                System.out.println("---------------- DigestUtils.md5Hex --------------")
//                if (jarName.endsWith(".jar")) {
//                    jarName = jarName.substring(0, jarName.length() - 4)
//                }
//                def dest = outputProvider.getContentLocation(jarName + md5Name, jarInput.contentTypes, jarInput.scopes, Format.JAR)
//                FileUtils.copyFile(jarInput.file, dest)
                processJarInput(jarInput, outputProvider)
            }
        }
        System.out.println("--------------结束transform了----------------")
    }


    private static void processDirectoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        File dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format
                .DIRECTORY)
        //将修改过的字节码copy到dest,就可以实现编译期间干预字节码的目的
        println("拷贝文件夹 $dest -----")
        FileUtils.copyDirectory(directoryInput.file, dest)
    }

    private static void processJarInput(JarInput jarInput, TransformOutputProvider outputProvider) {
        File dest = outputProvider.getContentLocation(jarInput.file.absolutePath, jarInput.contentTypes, jarInput.scopes, Format.JAR)
        //将修改过的字节码copy到dest,就可以实现编译期间干预字节码的目的
        println("拷贝文件 $dest -----")
        FileUtils.copyFile(jarInput.file, dest)
    }
}
