package Ecom.ServiceImpl;

import Ecom.Exception.ReviewException;
import Ecom.Model.Product;
import Ecom.Model.Review;
import Ecom.Model.User;
import Ecom.Repository.ProductRepository;
import Ecom.Repository.ReviewRepository;
import Ecom.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewRepository reviewRepository;

    private UserRepository userRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Product product;
    private User user;
    private Review review;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId(1);
        product.setReviews(new ArrayList<>());

        user = new User();
        user.setUserId(1);
        user.setReviews(new ArrayList<>());

        review = new Review();
        //review.setReviewId(100);
        review.setComment("Great product");
        review.setRating(5);
    }



    @Test
    void addReviewToProduct_ProductNotFound_ShouldThrowException() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        ReviewException ex = assertThrows(ReviewException.class,
                () -> reviewService.addReviewToProduct(1, 1, review));

        assertEquals("Product Not Found", ex.getMessage());
    }

    @Test
    void updateReviewToProduct_ShouldUpdateReview() throws ReviewException {
        Review existingReview = new Review();
        //existingReview.setReviewId(100);
        existingReview.setComment("Old comment");
        existingReview.setRating(3);

        when(reviewRepository.findById(100)).thenReturn(Optional.of(existingReview));

        review.setComment("Updated comment");
        review.setRating(4);

        Review result = reviewService.updateReviewToProduct(100, review);

        assertEquals("Updated comment", result.getComment());
        assertEquals(4, result.getRating());
    }

    @Test
    void updateReviewToProduct_NotFound_ShouldThrowException() {
        when(reviewRepository.findById(999)).thenReturn(Optional.empty());

        ReviewException ex = assertThrows(ReviewException.class,
                () -> reviewService.updateReviewToProduct(999, review));

        assertEquals("Review With Id 999Not Found In DataBase", ex.getMessage());
    }

    @Test
    void deleteReview_ShouldDeleteReview() throws ReviewException {
        when(reviewRepository.findById(100)).thenReturn(Optional.of(review));

        reviewService.deleteReview(100);

        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    void deleteReview_NotFound_ShouldThrowException() {
        when(reviewRepository.findById(999)).thenReturn(Optional.empty());

        ReviewException ex = assertThrows(ReviewException.class,
                () -> reviewService.deleteReview(999));

        assertEquals("Review With Id 999Not Found In DataBase", ex.getMessage());
    }

    @Test
    void getAllReviewOfProduct_ShouldReturnList() throws ReviewException {
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(reviewRepository.findAllReviewsByProductId(1)).thenReturn(reviews);

        List<Review> result = reviewService.getAllReviewOfProduct(1);

        assertEquals(1, result.size());
        assertEquals("Great product", result.get(0).getComment());
    }

    @Test
    void getAllReviewOfProduct_Empty_ShouldThrowException() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(reviewRepository.findAllReviewsByProductId(1)).thenReturn(new ArrayList<>());

        ReviewException ex = assertThrows(ReviewException.class,
                () -> reviewService.getAllReviewOfProduct(1));

        assertEquals("No Rewiew Of Given Product is Available", ex.getMessage());
    }

    @Test
    void getAllReviewOfProduct_InvalidProduct_ShouldThrowException() {
        when(productRepository.findById(999)).thenReturn(Optional.empty());

        ReviewException ex = assertThrows(ReviewException.class,
                () -> reviewService.getAllReviewOfProduct(999));

        assertEquals("Invalid Product id", ex.getMessage());
    }
}