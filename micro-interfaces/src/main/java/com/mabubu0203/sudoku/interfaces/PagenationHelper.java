package com.mabubu0203.sudoku.interfaces;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.io.Serializable;

import static lombok.AccessLevel.PUBLIC;

@Getter
@NoArgsConstructor(access = PUBLIC)
public class PagenationHelper implements Serializable {

    private final int MAX_DISP_PAGE = 5;
    private boolean hiddenPrev;
    private boolean hiddenNext;

    private boolean hiddenPage2;
    private boolean hiddenPage3;

    private boolean activePage1;
    private boolean activePage2;
    private boolean activePage3;

    private int page1PageValue;

    public PagenationHelper(Page<?> page) {
        int number = page.getNumber();
        int totalPages = page.getTotalPages();

        this.hiddenPrev = (number == 0);
        this.hiddenNext = ((totalPages == 0) || (number == totalPages - 1));

        this.hiddenPage2 = (totalPages <= 1);
        this.hiddenPage3 = (totalPages <= 2);

        this.activePage1 = (number == 0);
        this.activePage2 = ((MAX_DISP_PAGE - 3 <= totalPages) && (number + 1 == MAX_DISP_PAGE - 3));
        this.activePage3 =
                (((totalPages == MAX_DISP_PAGE - 2) && (number + 1 == MAX_DISP_PAGE - 2))
                        || ((MAX_DISP_PAGE - 2 < totalPages)
                        && (MAX_DISP_PAGE - 2 <= number + 1)
                        && (number + 1 < totalPages - 1)));

        if (totalPages <= MAX_DISP_PAGE) {
            this.page1PageValue = 0;
        } else {
            if (number + 1 <= MAX_DISP_PAGE - 2) {
                this.page1PageValue = 0;
            } else if ((MAX_DISP_PAGE - 1 <= number + 1) && (number + 1 <= totalPages - 2)) {
                this.page1PageValue = number - 2;
            } else {
                this.page1PageValue = totalPages - MAX_DISP_PAGE;
            }
        }
    }

}
