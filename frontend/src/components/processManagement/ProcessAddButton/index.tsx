import { useState } from 'react';

import plusIcon from '@assets/images/plus.svg';

import IconButton from '@components/common/IconButton';
import ProcessAddForm from '@components/processManagement/ProcessAddForm';

import S from './style';

interface ProcessAddButtonProps {
  dashboardId: string;
  applyFormId: string;
  priorOrderIndex: number;
}

export default function ProcessAddButton({ dashboardId, applyFormId, priorOrderIndex }: ProcessAddButtonProps) {
  const [isToggled, setIsToggled] = useState(false);

  const toggleProcessAddForm = () => {
    setIsToggled((prev) => !prev);
  };

  return (
    <S.Container>
      {isToggled ? (
        <ProcessAddForm
          dashboardId={dashboardId}
          applyFormId={applyFormId}
          priorOrderIndex={priorOrderIndex}
          toggleForm={toggleProcessAddForm}
        />
      ) : (
        <IconButton
          type="button"
          onClick={toggleProcessAddForm}
          size="sm"
          shape="square"
          outline
        >
          <img
            src={plusIcon}
            alt="플러스 아이콘"
          />
        </IconButton>
      )}
    </S.Container>
  );
}
