import { HiChevronLeft, HiChevronRight } from 'react-icons/hi2';
import IconButton from '@components/_common/atoms/IconButton';
import S from './style';

interface ProcessHeaderProps {
  processName: string;
  isCurrentProcess: boolean;
  isLastProcess: boolean;
  isFirstProcess: boolean;
  handleChangeProcess: (direction: 1 | -1) => void;
}

export default function InquireEvalHeader({
  processName,
  isCurrentProcess,
  isLastProcess,
  isFirstProcess,
  handleChangeProcess,
}: ProcessHeaderProps) {
  return (
    <S.Container>
      <IconButton
        size="sm"
        shape="round"
        outline={false}
        disabled={isFirstProcess}
        onClick={() => handleChangeProcess(-1)}
      >
        <HiChevronLeft />
      </IconButton>

      <S.ProcessNameContainer>
        {isCurrentProcess && <S.CurrentLabel>현재</S.CurrentLabel>}
        <S.ProcessName>{processName}</S.ProcessName>
      </S.ProcessNameContainer>

      <IconButton
        size="sm"
        shape="round"
        outline={false}
        disabled={isLastProcess}
        onClick={() => handleChangeProcess(1)}
      >
        <HiChevronRight />
      </IconButton>
    </S.Container>
  );
}
