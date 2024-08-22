import { HiOutlineClock, HiOutlineChat } from 'react-icons/hi';

import { useState, useRef, useEffect } from 'react';

import EllipsisIcon from '@assets/icons/ellipsis.svg';
import { PopOverMenuItem } from '@customTypes/common';

import IconButton from '@components/common/IconButton';
import PopOverMenu from '@components/common/PopOverMenu';
import formatDate from '@utils/formatDate';

import S from './style';

interface ApplicantCardProps {
  name: string;
  isRejected: boolean;
  createdAt: string;
  evaluationCount: number;
  averageScore: number;
  popOverMenuItems: PopOverMenuItem[];
  onCardClick: () => void;
}

export default function ApplicantCard({
  name,
  isRejected,
  createdAt,
  evaluationCount,
  averageScore,
  popOverMenuItems,
  onCardClick,
}: ApplicantCardProps) {
  const [isPopOverOpen, setIsPopOverOpen] = useState<boolean>(false);
  const optionButtonWrapperRef = useRef<HTMLDivElement>(null);

  const evaluationString = averageScore ? `★ ${averageScore.toFixed(1)}` : '평가 대기 중';

  const handleClickPopOverButton = (event: React.MouseEvent) => {
    event.stopPropagation();
    setIsPopOverOpen((prevState) => !prevState);
  };

  const handleClickOutside = (event: MouseEvent) => {
    if (optionButtonWrapperRef.current && !optionButtonWrapperRef.current.contains(event.target as Node)) {
      setIsPopOverOpen(false);
    }
  };

  const handleMouseLeave = () => {
    setIsPopOverOpen(false);
  };

  const cardClickHandler = (e: React.MouseEvent<HTMLDivElement>) => {
    e.stopPropagation();
    onCardClick();
  };

  useEffect(() => {
    if (isPopOverOpen) {
      document.addEventListener('mousedown', handleClickOutside);
    }

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [isPopOverOpen]);

  return (
    <S.CardContainer
      onMouseLeave={handleMouseLeave}
      onClick={cardClickHandler}
    >
      <S.CardDetail>
        <S.CardHeader>{name}</S.CardHeader>
        <S.CardEvaluationFlag
          averageScore={averageScore}
          evaluationCount={evaluationCount}
        >
          {evaluationString}
        </S.CardEvaluationFlag>
        <S.CardInfoContainer>
          <S.CardInfo>
            <HiOutlineClock size="1.2rem" />
            {formatDate(createdAt)}
          </S.CardInfo>

          <S.CardInfo>
            <HiOutlineChat size="1.2rem" />
            {evaluationCount}
          </S.CardInfo>
        </S.CardInfoContainer>
      </S.CardDetail>

      <S.OptionButtonWrapper>
        <div ref={optionButtonWrapperRef}>
          <IconButton
            type="button"
            outline={false}
            onClick={handleClickPopOverButton}
            disabled={isRejected}
          >
            <img
              alt="심사단계 이동 버튼 아이콘"
              src={EllipsisIcon}
            />
          </IconButton>
          <PopOverMenu
            isOpen={isPopOverOpen}
            setClose={() => setIsPopOverOpen(false)}
            items={popOverMenuItems}
            popOverPosition="3.5rem 0 0 -6rem"
          />
        </div>
      </S.OptionButtonWrapper>
    </S.CardContainer>
  );
}
