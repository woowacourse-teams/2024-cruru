import { useState } from 'react';

import plusIcon from '@assets/images/plus.svg';
import ProcessAddForm from '@components/ProcessAddForm';
import IconButton from '@components/IconButton';

import S from './style';

export default function ProcessAddButton() {
  const [isToggled, setIsToggled] = useState(false);

  const toggleProcessAddForm = () => {
    setIsToggled((prev) => !prev);
  };

  return (
    <S.Container>
      <S.HorizontalLine />

      {isToggled ? (
        <ProcessAddForm
          priorProcessId={0}
          toggleForm={toggleProcessAddForm}
        />
      ) : (
        <IconButton
          type="button"
          onClick={toggleProcessAddForm}
          size="sm"
          outline
        >
          <img
            src={plusIcon}
            alt="플러스 아이콘"
          />
        </IconButton>
      )}

      <S.HorizontalLine />
    </S.Container>
  );
}
