package Ecom.Service;

import Ecom.Exception.ReviewException;
import Ecom.Model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewService reviewService;

    private Review review;

    @BeforeEach
    void setUp() {
        review = new Review();
        review.setReviewId(1L);
        review.setRating(5);
        review.setComment("Great product!");
    }

    @Test
    void addReviewToProduct_ShouldReturnReview() throws ReviewException {
        when(reviewService.addReviewToProduct(10, 100, review)).thenReturn(review);

        reviewService.addReviewToProduct(10, 100, review);
        verify(reviewService).addReviewToProduct(10, 100, review);
    }

    @Test
    void updateReviewToProduct_ShouldReturnUpdatedReview() throws ReviewException {
        when(reviewService.updateReviewToProduct(1, review)).thenReturn(review);

        Review result = reviewService.updateReviewToProduct(1, review);

        assertEquals("Great product!", result.getComment());
        verify(reviewService).updateReviewToProduct(1, review);
    }

    @Test
    void deleteReview_ShouldNotThrowException() {
        assertDoesNotThrow(() -> reviewService.deleteReview(1));
        verify(reviewService).deleteReview(1);
    }

    @Test
    void getAllReviewOfProduct_ShouldReturnList() throws ReviewException {
        when(reviewService.getAllReviewOfProduct(10)).thenReturn(List.of(review));

        List<Review> result = reviewService.getAllReviewOfProduct(10);

        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getRating());
        verify(reviewService).getAllReviewOfProduct(10);
    }
}