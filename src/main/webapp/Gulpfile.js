var gulp = require('gulp');
var jspm = require('gulp-jspm');
var sourcemaps = require('gulp-sourcemaps');
var rename = require('gulp-rename');
var sass = require('gulp-sass');

gulp.task('js', function () {
	gulp.src('WEB-INF/javascripts/index.js')
	    .pipe(sourcemaps.init())
	    .pipe(jspm())
	    .pipe(rename('bundle.js'))
	    .pipe(sourcemaps.write('.'))
	    .pipe(gulp.dest('dist/'));
});

gulp.task('js:watch', function () {
	gulp.watch('WEB-INF/javascripts/**/*.js', ['js']);	
});


gulp.task('sass', function () {
  gulp.src('WEB-INF/stylesheets/main.scss')
	.pipe(sourcemaps.init())
    .pipe(sass().on('error', sass.logError))
    .pipe(rename('bundle.css'))
  	.pipe(sourcemaps.write())
    .pipe(gulp.dest('dist/'));
});

gulp.task('sass:watch', function () {
	gulp.watch('WEB-INF/stylesheets/**/*.scss', ['sass']);	
});

gulp.task('copy', function () {
	gulp.src(['WEB-INF/*images/**/*',
				'WEB-INF/*fonts/**/*',
				'jspm_packages/system.js', 
				'config.js'])
		.pipe(gulp.dest('dist/'));
});

gulp.task('watch', ['js:watch', 'sass:watch']);