package com.gailo22.notekeeper

object DataManager {
    val courses = HashMap<String, CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init {
        initializeCourses()
        initializeNotes()
    }

    private fun initializeCourses() {
        var course = CourseInfo("android_intent", "Android Programming with Intent")
        courses.set(course.courseId, course)

        course = CourseInfo("android_async", "Android Async Programming and Services")
        courses.set(course.courseId, course)

        course = CourseInfo("effective_java", "Effective Java")
        courses.set(course.courseId, course)
    }

    private fun initializeNotes() {
        var course = CourseInfo("android_intent", "Android Programming with Intent")
        var note = NoteInfo(course, "my android title", "my android intent course")
        notes.add(note)

        course = CourseInfo("android_async", "Android Async Programming and Services")
        note = NoteInfo(course, "my android async title", "my android async course")
        notes.add(note)

    }

}
