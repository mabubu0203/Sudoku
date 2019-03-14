var gulp = require('gulp');
var exec = require('gulp-exec');

gulp.task('install', function(){
    gulp.src('')
        .pipe(exec("../../../node_modules/bower/bin/bower install --save-dev Nico#v4.0.0"));
});

gulp.task('css', function(){
    gulp.src('bower_components/Nico/dist/css/*.css')
        .pipe(gulp.dest('static/css/'));
});

gulp.task('fonts', function(){
    gulp.src('bower_components/Nico/dist/fonts/*')
        .pipe(gulp.dest('static/fonts/'));
});

gulp.task('js', function(){
    gulp.src('bower_components/Nico/dist/js/*.js')
        .pipe(gulp.dest('static/js/'));
});

gulp.task('build', ['install','css','fonts','js']);