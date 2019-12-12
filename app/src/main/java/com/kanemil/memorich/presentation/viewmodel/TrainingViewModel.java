package com.kanemil.memorich.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.R;
import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.data.repository.Repository;
import com.kanemil.memorich.presentation.viewmodel.utils.ResourceWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

import javax.inject.Inject;

public class TrainingViewModel extends ViewModel {

    // inject
    private Repository mRepository;
    private ResourceWrapper mResourceWrapper;


    private LiveData<List<Card>> mCardsList = new MutableLiveData<>();
    private MutableLiveData<Integer> mCorrectAnswers = new MutableLiveData<>(0);
    private MutableLiveData<TreeSet<Integer>> mAnsweredCardsPositions = new MutableLiveData<>(new TreeSet<Integer>());
    private MutableLiveData<Boolean> mIsCardAlreadyAnswered = new MutableLiveData<>(false);
    private MutableLiveData<Map<String, Integer>> mTrainingScore = new MutableLiveData<>();
    private MutableLiveData<Integer> mFirstUnansweredCard = new MutableLiveData<>();

    private long mDeckId;

    @Inject
    TrainingViewModel(Repository repository, ResourceWrapper resourceWrapper) {
        mRepository = repository;
        mResourceWrapper = resourceWrapper;
    }

    public void setDeckId(long deckId) {
        mDeckId = deckId;
        loadCards();
    }

    public LiveData<List<Card>> getCardsList() {
        return mCardsList;
    }

    public LiveData<Integer> getCorrectAnswers() {
        return mCorrectAnswers;
    }

    public LiveData<TreeSet<Integer>> getAnsweredCardsPositions() {
        return mAnsweredCardsPositions;
    }

    public LiveData<Boolean> getIsCardAlreadyAnswered() {
        return mIsCardAlreadyAnswered;
    }

    public LiveData<Map<String, Integer>> getTrainingScore() {
        return mTrainingScore;
    }

    public LiveData<Integer> getFirstUnansweredCard() {
        return mFirstUnansweredCard;
    }

    private void loadCards() {
        mCardsList = mRepository.getCards(mDeckId);
    }

    public void checkIfCardAlreadyAnswered(int position) {
        if (mAnsweredCardsPositions.getValue() != null) {
            if (mAnsweredCardsPositions.getValue().contains(position)) {
                mIsCardAlreadyAnswered.setValue(true);
            } else {
                mIsCardAlreadyAnswered.setValue(false);
            }
        }
    }

    public void finishOrContinueTraining(boolean incrementOrNot, int position) {
        markCardAsAnswered(position);
        incrementCorrectAnswersNumber(incrementOrNot);
        checkIfAllCardsAreAnswered();
    }

    private void incrementCorrectAnswersNumber(boolean incrementOrNot) {
        if (mCorrectAnswers.getValue() != null && mCorrectAnswers.getValue() < Objects.requireNonNull(mCardsList.getValue()).size()) {
            if (incrementOrNot) {
                mCorrectAnswers.setValue(mCorrectAnswers.getValue() + 1);
            }
        }
    }

    private void markCardAsAnswered(int position) {
        TreeSet<Integer> answeredCardPositions = new TreeSet<>(Objects.requireNonNull(mAnsweredCardsPositions.getValue()));
        answeredCardPositions.add(position);
        mAnsweredCardsPositions.setValue(answeredCardPositions);
        updateFirstUnansweredCardPosition();
    }

    private void checkIfAllCardsAreAnswered() {
        if (mCardsList.getValue() != null && mAnsweredCardsPositions.getValue() != null) {
            if (mCardsList.getValue().size() == mAnsweredCardsPositions.getValue().size() && mCorrectAnswers.getValue() != null) {
                postResult();
            }
        }
    }

    private void postResult() {
        final float result = mCorrectAnswers.getValue() * 1f / mCardsList.getValue().size() * 100;
        final Map<String, Integer> map = new HashMap<>();
        final String text = mResourceWrapper.getString(R.string.training_result, result);
        map.put(text, (int) result);
        mTrainingScore.setValue(map);
    }

    private void updateFirstUnansweredCardPosition() {
        if (mCardsList.getValue() != null) {
            for (int i = 0; i < mCardsList.getValue().size(); i++) {
                if (mAnsweredCardsPositions.getValue() != null) {
                    if (!mAnsweredCardsPositions.getValue().contains(i)) {
                        mFirstUnansweredCard.setValue(i);
                        return;
                    }
                }
            }
        }
    }
}
