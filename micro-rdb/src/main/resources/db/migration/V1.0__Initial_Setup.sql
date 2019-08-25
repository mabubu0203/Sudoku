CREATE TABLE IF NOT EXISTS sudoku.answer_info_tbl
(
  no BIGINT(10) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  type TINYINT(1) NOT NULL,
  answerkey VARCHAR(81) UNIQUE NOT NULL,
  keyhash VARCHAR(64) UNIQUE NOT NULL ,
  create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

--COMMENT ON TABLE sudoku.answer_info_tbl IS 'Answer';
--COMMENT ON COLUMN sudoku.answer_info_tbl.no IS 'No';
--COMMENT ON COLUMN sudoku.answer_info_tbl.type IS 'Type';
--COMMENT ON COLUMN sudoku.answer_info_tbl.answerkey IS 'Answerkey';
--COMMENT ON COLUMN sudoku.answer_info_tbl.keyhash IS 'Keyhash';
--COMMENT ON COLUMN sudoku.answer_info_tbl.create_date IS '作成日';

CREATE TABLE IF NOT EXISTS sudoku.score_info_tbl
(
  no BIGINT(10) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  score MEDIUMINT(7) UNSIGNED DEFAULT 0 NOT NULL,
  name VARCHAR(64) DEFAULT '' NOT NULL,
  memo VARCHAR(64) DEFAULT '' NOT NULL,
  update_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY(no)
          REFERENCES answer_info_tbl(no)
          ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

--COMMENT ON TABLE sudoku.score_info_tbl IS 'Score';
--COMMENT ON COLUMN sudoku.score_info_tbl.no IS 'No';
--COMMENT ON COLUMN sudoku.score_info_tbl.score IS 'スコア';
--COMMENT ON COLUMN sudoku.score_info_tbl.name IS '名前';
--COMMENT ON COLUMN sudoku.score_info_tbl.memo IS 'メモ';
--COMMENT ON COLUMN sudoku.score_info_tbl.update_date IS '更新日';

ALTER TABLE answer_info_tbl ADD INDEX INDEX_ANSWERKEY(answerkey ASC);
ALTER TABLE answer_info_tbl ADD INDEX INDEX_KEYHASH(keyhash ASC);
