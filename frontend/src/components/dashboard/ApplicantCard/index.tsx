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
  createdAt: string;
  evaluationCount: number;
  popOverMenuItems: PopOverMenuItem[];
  onCardClick: () => void;
}

export default function ApplicantCard({
  name,
  createdAt,
  evaluationCount,
  popOverMenuItems,
  onCardClick,
}: ApplicantCardProps) {
  const [isPopOverOpen, setIsPopOverOpen] = useState<boolean>(false);
  const optionButtonWrapperRef = useRef<HTMLDivElement>(null);

  /**
   * 현재는 API 스펙상 지원자 카드에 평점(averageScore)을 보여줄 방법이 아직 없습니다.
   * 따라서 평가자 수를 기준으로 '평가 대기 중', '평가 완료'의 2가지 플래그로 우선 적용했습니다.
   * 추후 API 스펙 변경시 이에 대한 PR을 별도로 올릴 예정입니다.
   * - 2024-08-18 by 아르
   */
  // const evaluationString = averageScore ? `★ ${averageScore.toFixed(1)}` : '평가 대기 중';
  const evaluationString = evaluationCount ? '평가 완료' : '평가 대기 중';

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
          averageScore={0}
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
          >
            <img
              alt="테스트"
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
