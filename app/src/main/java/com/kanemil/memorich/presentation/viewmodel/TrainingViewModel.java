package com.kanemil.memorich.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kanemil.memorich.data.db.entity.Card;
import com.kanemil.memorich.data.repository.Repository;

import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

// TODO баг с неверным результатом (при двух картах видно хорошо)

public class TrainingViewModel extends ViewModel {
    private Repository mRepository;
    private LiveData<List<Card>> mCardsList = new MutableLiveData<>();
    private MutableLiveData<Integer> mCorrectAnswers = new MutableLiveData<>(0);
    private MutableLiveData<TreeSet<Integer>> mAnsweredCardsPositions = new MutableLiveData<>(new TreeSet<Integer>());
    private MutableLiveData<Boolean> mIsCardAlreadyAnswered = new MutableLiveData<>(false);
    private MutableLiveData<String> mTrainingScore = new MutableLiveData<>();
    private MutableLiveData<Integer> mFirstUnansweredCard = new MutableLiveData<>();

    private long mDeckId;

    TrainingViewModel(long deckId, Repository repository) {
        mDeckId = deckId;
        mRepository = repository;
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

    public LiveData<String> getTrainingScore() {
        return mTrainingScore;
    }

    public LiveData<Integer> getFirstUnansweredCard() {
        return mFirstUnansweredCard;
    }

    private void loadCards() {
        mCardsList = mRepository.getCards(mDeckId);
    }

    public void incrementCorrectAnswersNumber(boolean incrementOrNot) {
        if (mCorrectAnswers.getValue() != null && mCorrectAnswers.getValue() < Objects.requireNonNull(mCardsList.getValue()).size()) {
            if (incrementOrNot) {
                mCorrectAnswers.setValue(mCorrectAnswers.getValue() + 1);
            }
        }
    }

    public void markCardAsAnswered(int position) {
        TreeSet<Integer> answeredCardPositions = new TreeSet<>(Objects.requireNonNull(mAnsweredCardsPositions.getValue()));
        answeredCardPositions.add(position);
        mAnsweredCardsPositions.setValue(answeredCardPositions);
        updateFirstUnansweredCardPosition();
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

    public void checkIfAllCardsAreAnswered() {
        if (mCardsList.getValue() != null && mAnsweredCardsPositions.getValue() != null) {
            if (mCardsList.getValue().size() == mAnsweredCardsPositions.getValue().size() && mCorrectAnswers.getValue() != null) {
                mTrainingScore.setValue("Your result is: " + mCorrectAnswers.getValue() * 1f / mCardsList.getValue().size() * 100 + "%");
            }
        }
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
