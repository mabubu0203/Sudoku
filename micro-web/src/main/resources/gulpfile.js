var gulp = require('gulp');
var exec = require('gulp-exec');

gulp.task('install', function(){
    gulp.src('')
        .pipe(exec("../../../node_modules/bower/bin/bower install --save-dev Nico#v4.0.0"))
        .pipe(exec("../../../node_modules/bower/bin/bower install --save-dev bootstrap-datepicker#1.9.0"))
        .pipe(exec("../../../node_modules/bower/bin/bower install --save-dev bootstrap-validator#v0.11.9"))
    ;
});

gulp.task('css', function(){
    gulp.src('bower_components/Nico/dist/css/*.css')
        .pipe(gulp.dest('static/css/'));
    gulp.src('bower_components/bootstrap-datepicker/dist/css/*.css')
        .pipe(gulp.dest('static/css/'));
});

gulp.task('fonts', function(){
    gulp.src('bower_components/Nico/dist/fonts/*')
        .pipe(gulp.dest('static/fonts/'));
});

gulp.task('js', function(){
    gulp.src('bower_components/Nico/dist/js/*.js')
        .pipe(gulp.dest('static/js/'));
    gulp.src('bower_components/bootstrap-datepicker/dist/js/*.js')
        .pipe(gulp.dest('static/js/'));
    gulp.src('bower_components/bootstrap-datepicker/dist/locales/bootstrap-datepicker.ja.min.js')
        .pipe(gulp.dest('static/js/'));
    gulp.src('bower_components/bootstrap-validator/js/*.js')
        .pipe(gulp.dest('static/js/'));
});

gulp.task('build', ['install','css','fonts','js']);