import { useState } from 'react';

import EllipsisIcon from '@assets/icons/ellipsis.svg';
import S from './style';
import formatDate from '../../utils/formatDate';
import IconButton from '../IconButton';
import PopOverMenu from '../PopOverMenu';

import { PopOverMenuItem } from '@/types/common';

interface ApplicantCardProps {
  name: string;
  createdAt: string;
  popOverMenuItems: PopOverMenuItem[];
}

export default function ApplicantCard({ name, createdAt, popOverMenuItems }: ApplicantCardProps) {
  const [isPopOverMenuOpen, setIsPopOverMenuOpen] = useState<boolean>(false);

  const togglePopOverMenu = () => setIsPopOverMenuOpen((prevState) => !prevState);

  return (
    <S.CardContainer>
      <S.CardDetail>
        <S.CardHeader>{name}</S.CardHeader>
        <S.CardDate>{`지원 일자: ${formatDate(createdAt)}`}</S.CardDate>
      </S.CardDetail>
      <div>
        <IconButton
          type="button"
          outline={false}
          onClick={togglePopOverMenu}
        >
          <img
            alt="테스트"
            src={EllipsisIcon}
          />
        </IconButton>
        <PopOverMenu
          isOpen={isPopOverMenuOpen}
          items={popOverMenuItems}
        />
      </div>
    </S.CardContainer>
  );
}
