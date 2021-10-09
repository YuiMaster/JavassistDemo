package com.hd.plugin_gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class ChangePlugin implements Plugin<Project> {
    private Project project

    @Override
    void apply(Project _project) {
        this.project = _project
        System.out.println("========== 注册 HdChangeTransform ==========")
        project.android.registerTransform(new HdChangeTransform(project))
    }
}