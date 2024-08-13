import { Process } from '@customTypes/process';
import { Fragment } from 'react/jsx-runtime';
import S from './style';
import ProcessModifyForm from '../ProcessModifyForm';
import ProcessAddButton from '../ProcessAddButton';

interface ProcessManageBoardProps {
  dashboardId: string;
  postId: string;
  processes: Process[];
}

export default function ProcessManageBoard({ dashboardId, postId, processes }: ProcessManageBoardProps) {
  const FIRST_INDEX = 0;
  const LAST_INDEX = processes.length - 1;
  const isAddable = processes.length < 5;

  return (
    <S.Container>
      {processes.map((process, index) => (
        <Fragment key={process.processId}>
          <ProcessModifyForm
            dashboardId={dashboardId}
            postId={postId}
            process={process}
            isDeletable={index !== FIRST_INDEX && index !== LAST_INDEX}
          />

          {isAddable && index !== LAST_INDEX && (
            <ProcessAddButton
              dashboardId={dashboardId}
              postId={postId}
              priorOrderIndex={process.orderIndex}
            />
          )}
        </Fragment>
      ))}
    </S.Container>
  );
}
