import { useRef } from 'react';
import { MdCalendarMonth } from 'react-icons/md';
import S from './style';
import IconButton from '../IconButton';

interface DateInputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label: string;
  innerText?: string;
}

export default function DateInput({ label, innerText, ...props }: DateInputProps) {
  const inputRef = useRef<HTMLInputElement | null>(null);

  const handleIconClick = () => {
    if (!inputRef.current?.disabled) {
      inputRef.current?.showPicker();
    }
  };

  return (
    <S.Container onClick={handleIconClick}>
      <S.Wrapper>
        <S.Label>{label}</S.Label>
        <S.Text>{innerText}</S.Text>
        <S.Input
          ref={inputRef}
          type="date"
          {...props}
          required
        />
      </S.Wrapper>

      <IconButton
        outline={false}
        size="sm"
        color="white"
      >
        <S.Icon>
          <MdCalendarMonth size="2rem" />
        </S.Icon>
      </IconButton>
    </S.Container>
  );
}
